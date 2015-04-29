( function() {

  'use strict';
  angular.module( 'tamilapp.controllers' ).controller( 'tamilWordSplitController', 
  function( $scope, tamilWordSplit, splitRulesTypes, wordTypes ) {
	$scope.wordTypes = wordTypes; //Is this a better way?
    $scope.splitResults = []; 
	$scope.selectedRule ={};
	$scope.wordToSplitRule ={};
	$scope.splitRulesTypesCache = splitRulesTypes;
	$scope.splitIntoWords = function() {
	var deferred = tamilWordSplit.tamilWordSplitAsync('Test');
	deferred.then(function(data) {	
	$scope.splitResults = data;
	});
	};

  } );

} )( window.angular );