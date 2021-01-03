import { Directive, ElementRef, HostBinding, HostListener, OnInit, Renderer2 } from "@angular/core";

@Directive({
    selector: '[appDropdown]'
})
export class DropdownDirective implements OnInit{

    @HostBinding('class.open') isOpen= false;

    constructor(private elRef: ElementRef) {}
    ngOnInit(): void {
        
    }
/* 
    @HostListener('click') toggleOpen(eventData: Event){
        this.isOpen=!this.isOpen;
    } */


    @HostListener('document:click', ['$event']) toggleOpen(event: Event) {
        this.isOpen = this.elRef.nativeElement.contains(event.target) ? !this.isOpen : false;
      }
}