( function() {
  'use strict';

  angular.module( 'tamilapp.routing' ).config(
    ['$stateProvider', '$urlRouterProvider', function( $stateProvider, $urlRouterProvider ) {
      
      // For any unmatched url, redirect to home page
      $urlRouterProvider.otherwise( '/' );      
      $stateProvider
	  .state( 'home', {
          url: '/home',
          templateUrl: '../app/views/home.html'
        } )
	  .state( 'contribute', {
          url: '/contribute',
          templateUrl: '../app/views/contribute.html'
        } )
	  .state( 'features', {
          url: '/features',
          templateUrl: '../app/views/features.html'
        } )
        .state( 'state1', {
          url: '/state1',
          templateUrl: '../app/views/view1.html'
        } )
        .state( 'state2', {
          url: '/state2',          
          templateUrl: '../app/views/view2.html'
        } )       
		.state( 'state3', {
          url: '/state3',          
          templateUrl: '../app/views/view3.html'
        })
		.state( 'state4', {
          url: '/state4',          
          templateUrl: '../app/views/view4.html'
        })
    }] );

} )();
