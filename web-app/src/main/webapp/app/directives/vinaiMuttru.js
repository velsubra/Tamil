( function( angular ) {
  'use strict';
  var module = angular.module( 'tamilapp.directives' );
  module.directive( 'dVinaiMuttru', function() {
    return {
      restrict: 'E',
	  replace:false,	 
	  scope : {
		category: '=',
		selectedWord: '='
	  },
      link: function( scope, element ) {		
	  	scope.setSelectedWord =function (word){
	      scope.selectedWord = word;
	   }
	  },
    templateUrl : "../app/directives/views/dvm-template.html"	
    }
	});   
} )( window.angular );

