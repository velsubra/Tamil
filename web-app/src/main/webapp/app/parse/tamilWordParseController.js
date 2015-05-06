( function() {

  'use strict';
  angular.module( 'tamilapp.controllers' ).controller( 'tamilWordParseController', 
  function( $scope, wordParser ) {
	$scope.parseResults = []; 
	$scope.wordToParse ='தொடர்மொழியின் ';
	$scope.isLoading = false;
	
	$scope.hasResults  =  function() {
	return angular.isDefined(
	$scope.parseResults && $scope.parseResults.words
	&& $scope.parseResults.words.length > 0
	);
	}
	
	$scope.parseWord = function() {
		$scope.isLoading = true;
		$scope.parseResults=[];
		var deferred = wordParser.getParsedWords($scope.wordToParse);
		deferred.then(function(data) {	
		$scope.isLoading = false;
		$scope.parseResults = data;
	});
	};
  } );

} )( window.angular );