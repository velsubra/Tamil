( function( angular ) {
  'use strict';
  var module = angular.module( 'tamilapp.directives' );
  module.directive( 'dTransliterationInput', [ 'lookup', function(lookup) {
    return {
      restrict: 'E',
	  replace:false,	 
	  scope : {
	  selectedWord: '='
	  },
      link: function( scope, element ) { 
		scope.focus = false;
		scope.searchText = "";
		scope.doTransliteration = function(searchText) {
		    scope.searchText = searchText;			
			return lookup.lookUpWordAsync(searchText);
		}
		scope.resetSearchText = function() {
			scope.selectedWord = scope.searchText;
		}
	},
    templateUrl : "../app/directives/views/dti-template.html"	
    }
	}]);   
} )( window.angular );

