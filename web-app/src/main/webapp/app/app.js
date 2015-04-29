( function() {
  'use strict';  
  angular.module( 'tamilapp.routing', ['ui.router'] );
  angular.module( 'tamilapp.controllers', [] );
  angular.module( 'tamilapp.directives', [] );
  angular.module( 'tamilapp.filters', [] );
  angular.module( 'tamilapp.services', [] );
  angular.module( 'tamilapp.constants', [] );
  angular.module( 'tamilapp', ['tamilapp.controllers', 'tamilapp.routing',  'tamilapp.directives', 'tamilapp.filters', 
  'tamilapp.constants', 'tamilapp.services', 'ui.bootstrap','ui.select', 'ngSanitize','ngResource'] );
  
  var app = angular.module( 'tamilapp');
  app.config(function($resourceProvider) {
  $resourceProvider.defaults.stripTrailingSlashes = false;
});
} )();

