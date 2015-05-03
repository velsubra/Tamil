var constantModule = angular.module( 'tamilapp.constants' );
constantModule.factory( 'serverPath', function() {
 var serverPathObj = new Object();
 serverPathObj.RESTLocation="/xyz/";
 return serverPathObj;
});