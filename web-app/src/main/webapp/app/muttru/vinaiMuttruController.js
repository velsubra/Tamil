( function() {

  'use strict';
  angular.module( 'tamilapp.controllers' ).controller( 'vinaiMuttruController', 
  function( $scope) {
	$scope.currentCategory = {"rowname":"thu","present":{"list":[{"value":"ஓடுகிறது","equation":"ஓடு+கிறு+அது"},{"value":"ஓடுகின்றது","equation":"ஓடு+கின்று+அது"}]},"past":{"list":[{"value":"ஓடினது","equation":"ஓடு+இன்+அது"},{"value":"ஓடியது","equation":"ஓடு+இ+அது"}]},"future":{"list":[{"value":"ஓடும்","equation":"ஓடு+உம்"}]}};
	
	$scope.currentWord ={"value":"ஓடும்","equation":"ஓடு+உம்"};
	
  } );

} )( window.angular );