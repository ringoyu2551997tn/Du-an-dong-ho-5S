myApp.controller("fail", function ($scope,$rootScope, $http,$window,checkOutDataService,$location) {
    setTimeout(function (){
        $window.location.href = '#index';
    },5000)
})
