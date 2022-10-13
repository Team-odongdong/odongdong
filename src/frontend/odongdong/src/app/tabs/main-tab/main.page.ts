import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { AlertController, ModalController } from '@ionic/angular';

import { Geolocation } from '@capacitor/geolocation';
import { BathroomService } from 'src/app/services/bathroom/bathroom.service';

declare let kakao;

const myIconUrl = '../assets//svg/map/current-location.svg';
const iconUrl = '../assets/svg/map/map-marker.svg';
const clickedIconUrl = '../assets/svg/map/marker-clicked.svg';

@Component({
  selector: 'app-main',
  templateUrl: 'main.page.html',
  styleUrls: ['main.page.scss']
})

export class MainPage implements OnInit {
  map: any;

  public initLatitude;
  public initLongitude;
  public locationSubscription: any;
  public bathroomList = [];
  
  public defaultMarker;
  public clickedMarker;
  public markerClicked = false;
  public selectedMarker = null;


  constructor(
    public bathroomService: BathroomService,
    public alertController: AlertController,
    public modalController: ModalController,
    public changeDetectorRef: ChangeDetectorRef,
  ) {}

  ngOnInit() {
    this.checkPermissions()
      .then(() => {
        this.getBathroomList();
      });
  }
  
  ngAfterViewInit() {
    this.createMap();
  }
  
  ionViewDidEnter() {    
    // this.trackLocation();
  }

  async getBathroomList() {
    const response = await this.bathroomService.get1kmBathroomList(this.initLongitude, this.initLatitude);
    if(response.status === 200) {
      this.bathroomList = response.data;
      console.log('bathroom', this.bathroomList);
    } else {
      console.log('fail to get list');      
    }
  }

  createMap() {
    setTimeout(() => {
      kakao.maps.load(() => {
        //맵 생성 -> 카메라의 중앙, 확대 정도 지정
        const options = {
            center: new kakao.maps.LatLng(this.initLatitude, this.initLongitude),
            level: 4
        };

        const mapRef = document.getElementById('map');

        this.map = new kakao.maps.Map(mapRef, options);

        this.setMarkerImages();
        this.addMarkers();

        // 맵 클릭 이벤트 리스너
        kakao.maps.event.addListener(this.map, 'click', () => {
          this.markerClicked = false;
          if(this.selectedMarker) {
            this.selectedMarker.setImage(this.defaultMarker);
          }

          if(!this.selectedMarker && this.modalController.getTop()) {
            this.modalController.dismiss();
          }
        });
      });
    }, 200);    
  }

  async checkPermissions() {
    const permissions = await Geolocation.checkPermissions();

    if(permissions.coarseLocation === 'denied' || permissions.location === 'denied') {
      await this.permissionAlert();
    } else {
      await this.getCurrentLocation();
    }
  }

  setMarkerImages() {
    this.defaultMarker = new kakao.maps.MarkerImage(
      iconUrl,
      new kakao.maps.Size(25, 25),
      {
        // offset: new kakao.maps.Point(13, 34),
        alt: 'marker img',
      }
    );

    this.clickedMarker = new kakao.maps.MarkerImage(
      clickedIconUrl,
      new kakao.maps.Size(70, 70),
      {
        offset: new kakao.maps.Point(35, 52),
        alt: 'marker img',
      }
    );
  }

  addMarkers() {
    this.bathroomList.forEach((place) => {
      const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(place.longitude, place.latitude),
          image: this.defaultMarker
      });
      marker.defaultMarker = this.defaultMarker;

      //마커 클릭 리스너
      kakao.maps.event.addListener(marker, 'click', () => {
        //마커 클릭 시 카메라 이동 정의
        const cameraMov = this.getCameraMovement(this.map.getLevel());
        const movedLocation = new kakao.maps.LatLng(place.longitude-cameraMov, place.latitude);

        //클릭된 마커가 없는 경우 -> 초기이므로, selectedMarker 값을 설정해 줘야 한다.
        if(!this.markerClicked) {
          this.markerClicked = true;
          this.selectedMarker = marker;
          marker.setImage(this.clickedMarker);
        }
  
        //클릭된 마커가 현재 마커가 아닌 경우
        if(this.selectedMarker !== marker) {          
          //새로 클릭된 마커는 이미지를 변경한다.
          marker.setImage(this.clickedMarker);
  
          //기존에 선택되어 있는 마커는 기본으로 바꾼다.
          this.selectedMarker.setImage(this.defaultMarker);
        }
  
        //현재 클릭된 마커를 선택된 마커로 업데이트한다.
        this.selectedMarker = marker;

        this.map.panTo(movedLocation);
      });
    });
  }

  getCameraMovement(level) {
    const levels = [0.00035, 0.0007, 0.0013, 0.003, 0.005, 0.01];
    
    if(level > 7) {
      return 0.01;
    } else {
      return levels[level-1];
    }
  }

  async getCurrentLocation() {
    const coordinates = await Geolocation.getCurrentPosition();
    console.log('coordinates', coordinates);    

    if(coordinates.timestamp > 0) {
      await this.setLatLng(coordinates.coords);
    } else {
      await this.failGetLocationAlert();
      console.log('fail to get current location');
    }
  }

  async setLatLng(coord: any) {
    this.initLatitude = coord.latitude;
    this.initLongitude = coord.longitude;

    console.log(this.initLatitude, this.initLongitude);    
  }
  
  // async trackLocation() {
  //   this.locationSubscription = await Geolocation.watchPosition(
  //     {
  //       enableHighAccuracy: true,
  //       timeout: 2000,
  //     },
  //     (position) => {
  //       console.log(this.locationSubscription);
        
  //       // this.initLatitude = position.coords.latitude;
  //       // this.initLongitude = position.coords.longitude;
  //     }
  //   );
  // }

  async permissionAlert() {
    const alert = await this.alertController.create({
      message: '위치 권한을 허용해주세요!',
      buttons: [
        {
          text: '닫기',
          handler: () => {}
        },
      ],
    });
    await alert.present();
  }

  async failGetLocationAlert() {
    const alert = await this.alertController.create({
      message: '위치 정보 가져오기 실패!',
      buttons: [
        {
          text: '닫기',
          handler: () => {}
        },
      ],
    });
    await alert.present();
  }

}
