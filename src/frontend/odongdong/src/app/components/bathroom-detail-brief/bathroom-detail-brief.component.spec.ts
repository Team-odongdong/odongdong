import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';
import { BathroomDetailBriefComponent } from './bathroom-detail-brief.component';

describe('BathroomDetailComponent', () => {
    let component: BathroomDetailBriefComponent;
    let fixture: ComponentFixture<BathroomDetailBriefComponent>;

    beforeEach(waitForAsync(() => {
        TestBed.configureTestingModule({
            declarations: [BathroomDetailBriefComponent],
            imports: [IonicModule.forRoot()],
        }).compileComponents();

        fixture = TestBed.createComponent(BathroomDetailBriefComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    }));

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
