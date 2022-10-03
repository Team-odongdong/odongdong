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
      const options = {
          center: new kakao.maps.LatLng(this.tempLat, this.tempLng),
          level: 3
      };

      this.map = new kakao.maps.Map(document.getElementById('map'), options);

      this.setMarkerImages();
      this.addPlaces();
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
        offset: new kakao.maps.Point(13, 34),
        alt: 'marker img',
      }
    );

    this.clickedMarker = new kakao.maps.MarkerImage(
      clickedIconUrl,
      new kakao.maps.Size(25, 25),
      {
        offset: new kakao.maps.Point(13, 34),
        alt: 'marker img',
      }
    );
  }

  addPlaces() {
    this.markers.forEach((place) => {      
      const marker = new kakao.maps.Marker({
          clickable: true,
          position: new kakao.maps.LatLng(place.lat, place.lng),
          image: this.defaultMarker
      });

      marker.setMap(this.map);
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
