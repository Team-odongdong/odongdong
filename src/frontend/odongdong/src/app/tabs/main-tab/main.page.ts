import { ChangeDetectorRef, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

import { AlertController, ModalController } from '@ionic/angular';

import { Geolocation } from '@capacitor/geolocation';

declare const kakao;

const myIconUrl = '../assets//svg/map/current-location.svg';
const iconUrl = '../assets/svg/map/map-marker.svg';
const clickedIconUrl = '../assets/svg/map/marker-clicked.svg';

@Component({
  selector: 'app-main',
  templateUrl: 'main.page.html',
  styleUrls: ['main.page.scss']
})

export class MainPage implements OnInit {
  // @ViewChild('map') map: ElementRef<HTMLElement>
  map: any;

  public initLatitude;
  public initLongitude;
  public locationSubscription: any;
  public defaultMarker;
  public clickedMarker;

  public markerClicked = false;
  public selectedMarker = null;

  public tempLat = 37.574445;
  public tempLng = 126.906140;

  markers = [
    { name: '공대2호관 쪽문나가는길', lat: this.tempLat+0.0003, lng: this.tempLng+0.0003 },
    { name: '공대2호관 뒷길(초등학교사이)', lat: this.tempLat+0.0005, lng: this.tempLng },
    { name: '체육관 좌측', lat: this.tempLat-0.0003, lng: this.tempLng-0.0003 },
    { name: '박물관뒤 주차장', lat: this.tempLat-0.0005, lng: this.tempLng }
  ];

  constructor(
    public alertController: AlertController,
    public modalController: ModalController,
    public changeDetectorRef: ChangeDetectorRef,
  ) {}

  ngOnInit(): void {
    this.checkPermissions();
    this.createMap();
  }

  async ionViewDidEnter() {
    // await this.trackLocation();

  }

  createMap() {
    setTimeout(() => {
      //맵 생성 -> 카메라의 중앙, 확대 정도 지정
      const options = {
          center: new kakao.maps.LatLng(this.tempLat, this.tempLng),
          level: 3
      };

      this.map = new kakao.maps.Map(document.getElementById('map'), options);

      this.setMarkerImages();
      this.addMarkers();

      //맵 클릭 이벤트 리스너
      kakao.maps.event.addListener(this.map, 'click', () => {
        this.markerClicked = false;
        this.selectedMarker.setImage(this.defaultMarker);
      });

    }, 300);

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
    this.markers.forEach((place) => {
      const marker = new kakao.maps.Marker({
          map: this.map,
          position: new kakao.maps.LatLng(place.lat, place.lng),
          image: this.defaultMarker
      });
      marker.defaultMarker = this.defaultMarker;

      //마커 클릭 리스너
      kakao.maps.event.addListener(marker, 'click', () => {  
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
      });
    });
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
