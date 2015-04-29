( function() {

  'use strict';
  angular.module( 'tamilapp.controllers' ).controller( 'lookUpController', function( $scope, lookup ) {
    $scope.searchResults = []; 
	$scope.selectedWord ={};
	
	$scope.loadAsync = function() {
	return lookup.lookUpWordAsync('Test');
	}
	$scope.load = function() {
	var deferred = lookup.lookUpWordAsync('Test');
	deferred.then(function(data) {	
	$scope.searchResults = data;
	});
	};

  } );

} )( window.angular );