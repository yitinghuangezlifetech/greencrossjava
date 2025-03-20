/* =================================
------------------------------------
	ProDent - Dentist Template
	Version: 1.0
 ------------------------------------ 
 ====================================*/


'use strict';


$(window).on('load', function() {
	/*------------------
		Preloder
	--------------------*/
	$(".loader").fadeOut(); 
	$("#preloder").delay(400).fadeOut("slow");
});

$(window).on('scroll', function() {
    var $menu = $('.header-section');
    var scrollTopVal = $(this).scrollTop();

    scrollTopVal > 110 ? $menu.addClass('header-section-fixed') : $menu.removeClass('header-section-fixed');
});
$(document).ready
(function($) {
	/*------------------
		Navigation
	--------------------*/
	$('.nav-switch').on('click', function(event) {
		$('.main-menu').slideToggle(400);
		event.preventDefault();
	});


	/*------------------
		Background Set
	--------------------*/
	$('.set-bg').each(function() {
		var bg = $(this).data('setbg');
		$(this).css('background-image', 'url(' + bg + ')');
	});

	/*------------------
		Main Menu
	--------------------*/
	$('.main-menu > li > a').click(function(event) {
		if($(window).width() < 768) {
			var hasSubMenu = $(this).parent().children().is('ul');
			hasSubMenu && event.preventDefault();
		}
	})

	/*------------------
		Hero Slider
	--------------------*/
	$('.hero-slider').owlCarousel({
		loop: true,
		nav: false,
		dots: true,
		mouseDrag: false,
		animateOut: 'fadeOut',
		animateIn: 'fadeIn',
		items: 1,
		autoplay: true,
		touchDrag: false
	});

	var dot = $('.hero-slider .owl-dot');
	dot.each(function() {
		var index = $(this).index() + 1;
		if(index < 10){
			$(this).html('0').append(index);
			$(this).append('<span>.</span>');
		}else{
			$(this).html(index);
			$(this).append('<span>.</span>');
		}
	});


	/*------------------
		Datepicker 
	--------------------*/
	


	/*------------------
		Testimonials 
	--------------------*/
	$('.testimonials-slider').owlCarousel({
		loop: true,
		nav: true,
		navText:['','<i class="fa fa-angle-right"></i>'],
		dots: false,
		margin: 128,
		center:true,
		responsive : {
			0 : {
				items: 1,
				margin: 0,
			},
			480 : {
				items: 1,
				margin: 0,
			},
			768 : {
				items: 1,
				margin: 0,
			},
			992 : {
				items: 3,
			}
		}
	});

	/*------------------
		Brands Slider
	--------------------*/
	$('.brands-slider').owlCarousel({
		loop: true,
		nav: false,
		dots: false,
		margin : 40,
		autoplay: true,
		responsive : {
			0 : {
				items: 1,
			},
			480 : {
				items: 2,
			},
			768 : {
				items: 4,
			},
			1200 : {
				items: 5,
			}
		}
	});

	/*------------------
		Popular Services
	--------------------*/
	$('.popular-services-slider').owlCarousel({
		loop: true,
		dots: false,
		margin : 40,
		autoplay: true,
		nav:true,
		navText:['<i class="fa fa-angle-left"></i>','<i class="fa fa-angle-right"></i>'],
		responsive : {
			0 : {
				items: 1,
			},
			768 : {
				items: 2,
			},
			991: {
				items: 3
			}
		}
	});

	
	/*------------------
		Accordions
	--------------------*/
	$('.panel-link').on('click', function (e) {
		$('.panel-link').removeClass('active');
		var $this = $(this);
		if (!$this.hasClass('active')) {
			$this.addClass('active');
		}
		e.preventDefault();
	});


	/*------------------
		Circle progress
	--------------------*/
	$('.circle-progress').each(function() {
		var cpvalue = $(this).data("cpvalue");
		var cpcolor = $(this).data("cpcolor");
		var cptitle = $(this).data("cptitle");
		var cpid 	= $(this).data("cpid");

		$(this).append('<div class="'+ cpid +'"></div><div class="progress-info"><h2>'+ cpvalue +'%</h2><p>'+ cptitle +'</p></div>');

		if (cpvalue < 100) {

			$('.' + cpid).circleProgress({
				value: '0.' + cpvalue,
				size: 191,
				thickness: 4,
				fill: cpcolor,
				emptyFill: "rgba(0, 0, 0, 0)"
			});
		} else {
			$('.' + cpid).circleProgress({
				value: 1,
				size: 191,
				thickness: 4,
				fill: cpcolor,
				emptyFill: "rgba(0, 0, 0, 0)"
			});
		}

	});

	/*------------------
		Tabs
	--------------------*/
	$('.tabs ul.tab-options li a').click(function() {
		var $li = $(this).parent();
		var $tabs = $li.parent().parent();
		$tabs.find('.active').removeClass('active');
		$li.addClass('active');
		$tabs.find('.tab-contents .tab-content').css('display','none').eq($li.index()).css('display','block');
	})

})
