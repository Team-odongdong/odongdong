import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  ViewChild,
} from '@angular/core';
import { Geolocation } from '@capacitor/geolocation';
import { AlertController, ModalController, Platform } from '@ionic/angular';

import { AddBathroomComponent } from 'src/app/components/add-bathroom/add-bathroom.component';
import { BathroomService } from 'src/app/services/bathroomService';
import { CommonService } from 'src/app/services/commonService';
import { BathroomDetailInfo } from 'src/app/types/bathroomInfo';
import { Distance, DistanceOptions } from 'src/app/types/distance';
import {
  createMarkerImage,
  deleteAllMarkers,
  setCameraMovement,
} from 'src/app/utils/naverMap';

declare let naver: any;

const myLocationIconUrl = '../assets/svgs/map/current-location.svg';
const iconUrl = '../assets/svgs/map/map-marker.svg';
const iconDisabledUrl = '../assets/svgs/map/map-marker-disabled.svg';
const iconRedUrl = '../assets/svgs/map/map-marker-red.svg';
const clickedIconUrl = '../assets/images/map/marker-clicked.png';
const clickedIconDisabledUrl =
  '../assets/images/map/marker-clicked-disabled.png';
const clickedIconRedUrl = '../assets/images/map/marker-clicked-red.png';
const addIconUrl = '../assets/images/map/add-new.png';

@Component({
  selector: 'app-main',
  templateUrl: 'main.page.html',
  styleUrls: ['main.page.scss'],
})
export class MainPage implements AfterViewInit {
  @ViewChild('detailContainer') detailContainer: ElementRef<HTMLElement>;
  @ViewChild('map') map: any;

  /** 초기 위치 : 건대입구역 */
  public initLatitude = 37.540372;
  public initLongitude = 127.069276;

  public currentLat: number;
  public currentLng: number;
  public locationSubscription: any;

  public bathroomList: Partial<BathroomDetailInfo>[];
  public bathroomInfo: any;
  public bathroomRadius: DistanceOptions = '1km';

  public defaultMarkerIcon: any;
  public clickedMarkerIcon: any;
  public addMarkerIcon: any;
  public myLocationMarkerIcon: any;
  public defaultDisabledMarkerIcon: any;
  public clickedDisabledMarkerIcon: any;
  public defaultRedMarkerIcon: any;
  public clickedRedMarkerIcon: any;

  public markerClicked = false;
  public selectedMarker: any;
  public addMarker: any;
  public myLocationMarker: any;
  public markerList: any[] = [];

  public mapLevel = 16;

  public userPlatform: string;

  constructor(
    public platform: Platform,
    public common: CommonService,
    public bathroomService: BathroomService,
    public alert: AlertController,
    public modal: ModalController,
    public changeDetectorRef: ChangeDetectorRef
  ) {
    this.setPlatform();
  }

  ngAfterViewInit() {
    this.setMarkerImages();
    this.createMap();
  }

  ionViewWillEnter() {
    setTimeout(() => {
      this.checkPermissions().then(() => {
        this.getBathroomList();
      });
      // .then(() => {
      //   this.trackLocation();
      // });
    }, 500);
  }

  setPlatform() {
    if (this.platform.is('android')) {
      this.userPlatform = 'md';
    } else if (this.platform.is('ios' || 'ipad' || 'iphone')) {
      this.userPlatform = 'ios';
    } else {
      this.userPlatform = 'web';
    }
  }

  async getBathroomList() {
    const response = await this.bathroomService.getBathrooms({
      latitude: this.currentLat,
      longitude: this.currentLng,
      distance: Distance[this.bathroomRadius],
    });

    if (response.data.code === 1000) {
      this.bathroomList = response.data.result as any;

      //move camera to current location
      this.moveToCurrentLocation(this.currentLat, this.currentLng);

      //add markers
      this.addMarkers();
    } else {
      await this.failToGetBathroomList();
    }
  }

  async getBathroomListPlain(isFromCurrentButton?: boolean) {
    const timeout = isFromCurrentButton ? 300 : 100;

    setTimeout(async () => {
      const currentCenter = this.map.getCenter();

      const response = await this.bathroomService.getBathrooms({
        latitude: currentCenter._lat,
        longitude: currentCenter._lng,
        distance: Distance[this.bathroomRadius],
      });
      if (response.data.code === 1000) {
        this.bathroomList = response.data.result as any;

        this.addMarkers();
      } else {
        await this.failToGetBathroomList();
      }
    }, timeout);
  }

  moveToCurrentLocation(lat: number, lng: number) {
    const currentLocation = new naver.maps.LatLng(lat, lng);

    // this.addMyLocationMarker(currentLocation);
    this.map.panTo(currentLocation, {
      duration: 700,
    });
  }

  addMyLocationMarker(current: any) {
    this.myLocationMarker = new naver.maps.Marker({
      map: this.map,
      position: new naver.maps.LatLng(current._lat, current._lng),
      icon: this.myLocationMarkerIcon,
    });

    this.myLocationMarker.setMap(this.map);
  }

  createMap() {
    setTimeout(() => {
      //맵 생성 -> 카메라의 중앙, 확대 정도 지정
      const options = {
        center: new naver.maps.LatLng(this.initLatitude, this.initLongitude),
        zoom: this.mapLevel,
        minZoom: 8,
      };

      const mapRef = document.getElementById('map');
      this.map = new naver.maps.Map(mapRef, options);

      //맵 클릭 이벤트 리스너 (좌클릭)
      this.mapLeftClickListener();

      if (this.userPlatform === 'web') {
        //맵 클릭 이벤트 리스너 (우클릭)
        this.mapRightClickListener();
      } else {
        //맵 클릭 이벤트 리스너 (롱 탭 - 모바일 전용)
        this.mapLongTabListener();
      }

      //맵 이동 감지
      this.mapDragEndListener();
    }, 300);
  }

  async checkPermissions() {
    const permissions = await Geolocation.checkPermissions();

    if (
      permissions.coarseLocation === 'denied' ||
      permissions.location === 'denied'
    ) {
      await this.permissionAlert();
    } else {
      await this.getCurrentLocation();
    }
  }

  setMarkerImages() {
    this.defaultMarkerIcon = createMarkerImage(iconUrl, 25, 25);
    this.defaultDisabledMarkerIcon = createMarkerImage(iconDisabledUrl, 25, 25);
    this.defaultRedMarkerIcon = createMarkerImage(iconRedUrl, 25, 25);

    this.clickedMarkerIcon = createMarkerImage(clickedIconUrl, 31, 43);
    this.clickedDisabledMarkerIcon = createMarkerImage(
      clickedIconDisabledUrl,
      31,
      43
    );
    this.clickedRedMarkerIcon = createMarkerImage(clickedIconRedUrl, 31, 43);

    this.addMarkerIcon = createMarkerImage(addIconUrl, 25, 35);

    this.myLocationMarkerIcon = createMarkerImage(myLocationIconUrl, 40, 40);
  }

  mapLeftClickListener() {
    naver.maps.Event.addListener(this.map, 'click', () => {
      this.resetMarkersOnMap();

      if (this.selectedMarker) {
        if (this.selectedMarker.getIcon().url === clickedIconUrl) {
          this.selectedMarker.setIcon(this.defaultMarkerIcon);
        } else if (
          this.selectedMarker.getIcon().url === clickedIconDisabledUrl
        ) {
          this.selectedMarker.setIcon(this.defaultDisabledMarkerIcon);
        } else if (this.selectedMarker.getIcon().url === clickedIconRedUrl) {
          this.selectedMarker.setIcon(this.defaultRedMarkerIcon);
        }
      }

      this.common.closePresentModal();
    });
  }

  mapRightClickListener() {
    naver.maps.Event.addListener(this.map, 'rightclick', (mouseEvent: any) => {
      this.resetMarkersOnMap();

      const currentLocation = mouseEvent.latlng;

      //TODO: refactor (addmarker & addmarkers)
      this.addMarker = new naver.maps.Marker({
        map: this.map,
        position: new naver.maps.LatLng(
          currentLocation._lat,
          currentLocation._lng
        ),
        icon: this.addMarkerIcon,
      });

      this.addMarker.setMap(this.map);
      this.addMarker.setZIndex(20);

      //show add bathroom component
      naver.maps.Event.addListener(this.addMarker, 'click', () => {
        this.showAddBathroomModal(currentLocation._lat, currentLocation._lng);
      });
    });
  }

  mapLongTabListener() {
    naver.maps.Event.addListener(this.map, 'longtap', (mouseEvent: any) => {
      this.resetMarkersOnMap();

      const currentLocation = mouseEvent.latlng;

      this.addMarker = new naver.maps.Marker({
        map: this.map,
        position: new naver.maps.LatLng(
          currentLocation._lat,
          currentLocation._lng
        ),
        icon: this.addMarkerIcon,
      });

      this.addMarker.setMap(this.map);
      this.addMarker.setZIndex(20);

      //show add bathroom component
      naver.maps.Event.addListener(this.addMarker, 'click', () => {
        this.showAddBathroomModal(currentLocation._lat, currentLocation._lng);
      });
    });
  }

  mapDragEndListener() {
    naver.maps.Event.addListener(this.map, 'dragend', () => {
      deleteAllMarkers(this.markerList);

      this.getBathroomListPlain();
    });
  }

  /** 클릭된 마커와, 추가하기 마커를 (존재한다면) 삭제한다. */
  resetMarkersOnMap() {
    this.markerClicked = false;
    if (this.addMarker) {
      this.addMarker.setMap(null);
      this.addMarker = null;
    }
  }

  //TODO: refactor (addmarker & addmarkers)
  addMarkers() {
    this.bathroomList.forEach((place) => {
      let currentMarkerIcon;
      if (place.isOpened === 'Y') {
        currentMarkerIcon = this.defaultMarkerIcon;
      } else if (place.isOpened === 'U') {
        currentMarkerIcon = this.defaultDisabledMarkerIcon;
      } else if (place.isOpened === 'N') {
        currentMarkerIcon = this.defaultRedMarkerIcon;
      }

      const marker = new naver.maps.Marker({
        map: this.map,
        position: new naver.maps.LatLng(place.latitude, place.longitude),
        icon: currentMarkerIcon,
      });

      //detail component를 위한 값 세팅
      marker.bathroomInfo = this.genBathroomInfo(place);

      this.markerList.push(marker);
      marker.setMap(this.map);

      //마커 클릭 리스너
      this.markerLeftClickListener(marker, place);
    });
  }

  markerLeftClickListener(markerRef: any, place: any) {
    //마커 클릭 리스너
    naver.maps.Event.addListener(markerRef, 'click', () => {
      this.bathroomInfo = markerRef.bathroomInfo;

      //마커 클릭 시 카메라 이동 정의
      const cameraMov = setCameraMovement(this.map.getZoom());

      const movedLocation = new naver.maps.LatLng(
        place.latitude - cameraMov,
        place.longitude
      );
      this.map.panTo(movedLocation, {
        duration: 400,
      });

      //클릭된 마커가 없는 경우 -> 초기이므로, selectedMarker 값을 설정해 줘야 한다.
      if (!this.markerClicked) {
        this.markerClicked = true;
        this.selectedMarker = markerRef;

        place.isOpened === 'Y'
          ? markerRef.setIcon(this.clickedMarkerIcon)
          : markerRef.setIcon(this.clickedDisabledMarkerIcon);

        if (place.isOpened === 'Y') {
          markerRef.setIcon(this.clickedMarkerIcon);
        } else if (place.isOpened === 'U') {
          markerRef.setIcon(this.clickedDisabledMarkerIcon);
        } else if (place.isOpened === 'N') {
          markerRef.setIcon(this.clickedRedMarkerIcon);
        }
      }

      //클릭된 마커가 현재 마커가 아닌 경우
      if (this.selectedMarker !== markerRef) {
        this.markerClicked = false;
        this.changeDetectorRef.detectChanges();

        //새로 클릭된 마커는 이미지를 변경한다.
        const currentMarkerRefUrl = markerRef.getIcon().url;
        if (currentMarkerRefUrl === iconUrl) {
          markerRef.setIcon(this.clickedMarkerIcon);
        } else if (currentMarkerRefUrl === iconDisabledUrl) {
          markerRef.setIcon(this.clickedDisabledMarkerIcon);
        } else if (currentMarkerRefUrl === iconRedUrl) {
          markerRef.setIcon(this.clickedRedMarkerIcon);
        }

        //기존에 선택되어 있는 마커는 기본으로 바꾼다.
        const selectedMarkerIconUrl = this.selectedMarker.getIcon().url;
        if (selectedMarkerIconUrl === clickedIconUrl) {
          this.selectedMarker.setIcon(this.defaultMarkerIcon);
        } else if (selectedMarkerIconUrl === clickedIconDisabledUrl) {
          this.selectedMarker.setIcon(this.defaultDisabledMarkerIcon);
        } else if (selectedMarkerIconUrl === clickedIconRedUrl) {
          this.selectedMarker.setIcon(this.defaultRedMarkerIcon);
        }
        this.selectedMarker.setZIndex(5);

        this.markerClicked = true;
        this.changeDetectorRef.detectChanges();
      }

      //현재 클릭된 마커를 선택된 마커로 업데이트한다.
      this.selectedMarker = markerRef;
      this.selectedMarker.setZIndex(10);
    });
  }

  async getCurrentLocation() {
    try {
      const coordinates = await Geolocation.getCurrentPosition();

      if (coordinates.timestamp > 0) {
        await this.setLatLng(coordinates.coords);
      } else {
        await this.failGetLocationAlert();
      }
    } catch (error) {
      // this.failGetLocationAlert();
      console.debug('fail to get current location');
    }
  }

  async setLatLng(coord: any) {
    this.currentLat = coord.latitude;
    this.currentLng = coord.longitude;
  }

  async trackLocation() {
    this.locationSubscription = await Geolocation.watchPosition(
      {
        enableHighAccuracy: true,
        timeout: 3000,
      },
      (position: any) => {
        this.currentLat = position.coords.latitude;
        this.currentLng = position.coords.longitude;
      }
    );
  }

  async permissionAlert() {
    const alert = await this.alert.create({
      message: '위치 권한을 허용해주세요!',
      buttons: [
        {
          text: '닫기',
          handler: () => {},
        },
      ],
    });
    await alert.present();
  }

  async failGetLocationAlert() {
    const alert = await this.alert.create({
      subHeader: '위치 정보를 가져오지 못했습니다.',
      message: '네트워크 상태를 확인하고, 새로고침을 해주세요!',
      buttons: [
        {
          text: '닫기',
          handler: () => {},
        },
      ],
    });
    await alert.present();
  }

  async failToGetBathroomList() {
    const alert = await this.alert.create({
      subHeader: '화장실 목록을 가져오지 못했습니다.',
      message: '네트워크 상태를 확인하고, 새로고침을 해주세요!',
      buttons: [
        {
          text: '닫기',
          handler: () => {},
        },
      ],
    });
    await alert.present();
  }

  async showAddBathroomModal(lat: number, lng: number) {
    const cameraMov = setCameraMovement(this.map.getZoom());
    const movedLocation = new naver.maps.LatLng(lat - cameraMov - 0.001, lng);
    this.map.panTo(movedLocation, {
      duration: 700,
    });

    const modal = await this.modal.create({
      component: AddBathroomComponent,
      cssClass: 'add-bathroom-modal',
      componentProps: {
        lat,
        lng,
      },
      showBackdrop: false,
      canDismiss: true,

      breakpoints: [0, 0.5, 0.75, 1],
      initialBreakpoint: 0.75,
      backdropDismiss: false,
      backdropBreakpoint: 0.75,
    });
    await modal.present();
  }

  genBathroomInfo(data: any) {
    const info = {
      id: data.bathroomId,
      title: data.title,
      rate: data.rate,
      isLocked: data.isLocked,
      imageUrl: data.imageUrl,
      isOpened: data.isOpened,
      operationTime: data.operationTime,
      address: data.address,
      addressDetail: data.addressDetail,
      isUnisex: data.isUnisex,
      latitude: data.latitude,
      longitude: data.longitude,
    };

    return info;
  }

  moveToCurrentButton() {
    const currentLocation = new naver.maps.LatLng(
      this.currentLat,
      this.currentLng
    );
    this.map.morph(currentLocation, 16, {
      duration: 700,
    });

    deleteAllMarkers(this.markerList);
    this.getBathroomListPlain(true);
  }

  zoomIn() {
    this.mapLevel = this.map.getZoom();
    this.mapLevel += 1;
    this.map.setZoom(this.mapLevel, true);
  }

  zoomOut() {
    this.mapLevel = this.map.getZoom();
    this.mapLevel -= 1;
    this.map.setZoom(this.mapLevel, true);
  }

  refresh() {
    window.location.reload();
  }

  onFilterChanged(event: { value: DistanceOptions }) {
    this.bathroomRadius = event.value;
    console.log('current data', this.bathroomRadius);
  }
}
