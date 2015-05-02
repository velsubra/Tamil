( function( angular ) {
  'use strict';
  var module = angular.module( 'tamilapp.directives' );
  module.directive( 'dTransliterationInput', [ 'wordSuggestor', function(wordSuggestor) {
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
			return wordSuggestor.getWordSuggestion(searchText);
		}
		scope.resetSearchText = function() {
			scope.selectedWord = scope.searchText;
		}
	},
    templateUrl : "../app/directives/views/dti-template.html"	
    }
	}]);   
} )( window.angular );

