var myApp = angular.module("myApp", ["ngRoute"]);
myApp.config(function ($routeProvider, $locationProvider,$httpProvider) {
    $httpProvider.interceptors.push('responseObserver');
    $locationProvider.hashPrefix("");
    $routeProvider
        // .when("/", {
        //   templateUrl: "page/trangchu.html",
        // })
        .when("/", {
            templateUrl: "page/trangchu.html",
            controller :"homeCtrl"
        })
        .when("/about", {
            templateUrl: "page/about-us.html",
        })
        .when("/contact", {
            templateUrl: "page/contact-us.html",
        })
        .when("/blog", {
            templateUrl: "page/blog.html",
        })
        .when("/sanpham", {
            templateUrl: "page/shop-gird.html",
            controller: "SanPhamController"
        })
        .when("/account", {
            templateUrl: "page/my-account.html",
            controller: "ThongTinCaNhanController"
        })
        .when("/cart", {
            templateUrl: "page/cart.html",
            controller :"cartCtrl"
        })
        .when("/signup", {
            templateUrl: "page/signup.html",
            controller: "registerCtrl"
        })
        .when("/login", {
            templateUrl: "page/signin.html",
            controller:"loginCtrl"
        })
        .when("/address", {
        templateUrl: "page/address.html",
    })
        .when("/shipping", {
        templateUrl: "page/checkout-shipping.html",
    })

        .when("/checkout", {
            templateUrl: "page/checkout.html",
            controller: "ThanhToanCtrl"
        })

        .when("/chitietsanpham/:idSp", {
            templateUrl: "page/single-product.html",
            controller: "TrangChiTietSanPhamController"
        })
        .when("/my-info", {
            templateUrl: "/page/my-info.html",
            controller: "ThongTinCaNhanController"
        })
        .when("/my-address", {
            templateUrl: "/page/address.html",
            controller: "AddressCtrl"
        })
        .when("/success", {
            templateUrl: "page/success.html",
            controller: "success"
        })
        .when("/fail", {
            templateUrl: "page/fail.html",
            controller: "fail"
        })

        .when("/history/all", {
            templateUrl: "page/historyAll.html",
            controller: "historyCtrl"
        })
        .when("/history/0", {
            templateUrl: "page/DHChoThanhToan.html",
            controller: "historyChoCtrl"
        })
        .when("/history/1", {
            templateUrl: "page/DHDangChuanBi.html",
            controller: "historyWaitCtrl"
        })
        .when("/history/2", {
            templateUrl: "page/DHDangGiao.html",
            controller: "historyShippingCtrl"
        })
        .when("/history/3", {
            templateUrl: "page/DHHoanThanh.html",
            controller: "historyDoneCtrl"
        })
        .when("/history/4", {
            templateUrl: "page/DHDaHuy.html",
            controller: "historyCancelCtrl"
        })
        .when("/history/5", {
            templateUrl: "page/DHHoanTra.html",
            controller: "historyReturnCtrl"
        })
        .when("/changePass", {
            templateUrl: "page/changePass.html",
            controller: "ChangePassCtrl"
        })
        .when("/chinhSach", {
            templateUrl: "page/chinhSach.html",
        })
        .when("/forgotPassword", {
            templateUrl: "page/forgotPass.html",
            controller : "forgotCtrl"
        })
        .when("/changePassword/:token", {
            templateUrl: "page/forgotpassUpdate.html",
            controller : "forgotUpdateCtrl"
        })
        .otherwise({
            redirectTo: "/",
        });
});

// .run(function ($rootScope, $http, $location) {
//   // Keep user logged in after page refresh
//   let user = localStorage.getItem("currentUser");
//   console.log(user)
//  let currentUser = user ? JSON.parse(user) : {};
//   if (currentUser) {
//     $rootScope.currentUser = currentUser;
//     $http.defaults.headers.common.Authorization = "Bearer " + currentUser.token;
//   }
//   $rootScope.$on("$locationChangeStart", function(event, next, current) {
//     var publicPages = ["#"];
//     var restrictPage = publicPages.indexOf($location.path()) === -1;
//     if(restrictPage && !currentUser) {
//       $location.path("/login");
//     }
//   });
// });



myApp.factory('responseObserver', function responseObserver($q, $window) {
    return {
        'responseError': function(errorResponse) {
            switch (errorResponse.status) {
                case 403:
                    $window.localStorage.removeItem('currentUser');
                    // $http.defaults.headers.common.Authorization = "";
                    $window.location.reload();
                    break;
                // case 500:
                //   $window.location.href = '/login';
                //   break;
            }
            return $q.reject(errorResponse);
        }
    };
});


myApp.controller("indexCtrl", function ($rootScope,$scope, $http,$window, $location){
    let currentUser = localStorage.getItem("currentUser");
    if(currentUser){
        $scope.currentUser = currentUser ? JSON.parse(currentUser) : {};
        $rootScope.currentUser =$scope.currentUser;
        $window.localStorage.setItem('currentUser', JSON.stringify($scope.currentUser));
        $http.defaults.headers.common.Authorization = "Bearer " + $scope.currentUser.token;
    }
    else{
        $rootScope.currentUser ="";
        $http.defaults.headers.common.Authorization = "";
    }

    $rootScope.logouts = function (){
        $window.localStorage.removeItem('currentUser');
        $http.defaults.headers.common.Authorization = "";
        Swal.fire({
            icon: "warning",
            title: "Đã đăng xuất!",
            text: "Bạn hãy đăng nhập để tiếp tục mua hàng nhé!",
            showConfirmButton: true,
            closeOnClickOutside: false,
            allowOutsideClick: false,
            timer: 1600,
        });
        setTimeout(function (){
            $location.path("/login")
            $window.location.reload();
        },2000)

    }
})