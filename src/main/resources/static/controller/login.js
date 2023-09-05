myApp.controller("loginCtrl", function ($scope,$rootScope ,$http,$location, $window) {

    $scope.errorMessages="";

    $scope.currentUser ={
        idKhachHang:"",
        username :"",
        token :""
    };
    $scope.logins = function() {
        $scope.loginRequest = {
            username : $scope.username,
            password : $scope.password
        }


        $http.post('/api/login', $scope.loginRequest ).then((resp) => {
            if ( resp.data.status == 200) {
                $scope.currentUser.username = resp.data.username
                $scope.currentUser.token =resp.data.token
                $scope.currentUser.idKhachHang =resp.data.idKhachHang
                $rootScope.currentUser =$scope.currentUser;
                $window.localStorage.setItem('currentUser', JSON.stringify($scope.currentUser));
                $http.defaults.headers.common.Authorization = "Bearer " + resp.data.token;

                Swal.fire({
                    icon: "success",
                    title:  resp.data.message,
                    text: "Quay lại trang chủ!",
                    timer: 5600,
                });

                $window.location.href = '#index';
                $window.location.reload();
                $scope.error = false;
            }
        }).catch(error =>{
            $scope.errorMessages = error.data.message;
            $rootScope.authenticated = false;
            $location.path("/login");
            $scope.error = true;

        });

    };




})

myApp.controller("ChangePassCtrl", function ($scope,$rootScope ,$http,$location, $window){
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if(currentUser == null) {
    Swal.fire({
        icon: "warning",
        title: "Bạn chưa đăng nhập !",
        text: "Hãy đăng nhập để tiếp tục shopping!",
        showConfirmButton: true,
        closeOnClickOutside: true,
        timer: 5600,
    });
    $window.location.href = '#login';
}
    $scope.changePass = function (){

            let changeRequest = angular.copy($scope.changeRequest);
            console.log(changeRequest)
            $http.post('/api/changePass', changeRequest).then((resp) => {
                if (resp.status == 200) {
                    Swal.fire({
                        icon: "success",
                        title: "Thành công!",
                        text: "Bạn hãy đăng nhập lại để tiếp tục sử dụng dịch vụ nhé!",
                        timer: 5600,
                    });
                    $window.localStorage.removeItem('currentUser');
                    $http.defaults.headers.common.Authorization = "";
                    $window.location.href = '#login';
                }
            }).catch(error => {
                $scope.errorMessages = error.data.message;
            });

    }


})
myApp.controller("forgotCtrl", function ($scope,$rootScope ,$http,$location, $window){
$scope.data = {
    message :"",
    token :"",
    error :""
};
    $scope.errorMessages =""
    $scope.forgotPass = function (){
        $http.post(`/api/resetPassword?email=${$scope.username}`).then((resp) => {
            $scope.data = resp.data;
            Swal.fire({
                icon: "Success",
                title: "Thông báo!",
                text: "Gửi thành công! Bạn hãy check mail để nhận được thông tin cập nhập nhé !",
                showConfirmButton: true,
                closeOnClickOutside: true,
                timer: 10600,
            });
        }).catch(error => {
                $scope.errorMessages = "Email không tồn tại";
        });
    }

})
myApp.controller("forgotUpdateCtrl", function ($scope,$rootScope,$routeParams,$http,$location, $window){
$scope.data = {
    message :"",
    token :"",
    error :""
};
$scope.init = function (){
    $http.get(`/api/user/changePassword/${$routeParams.token}`).then((resp) => {
        console.log(resp.data,"resssssssssssssssssssssssssssss")
        if(resp.data.status == "OK"){
            console.log("oke")
        }
    }).catch(error => {
        Swal.fire({
            icon: "warning",
            title: "Thông báo!",
            text: "Mã hết hạn!",
            showConfirmButton: true,
            closeOnClickOutside: true,
            timer: 5600,
        });
        $window.location.href = '#login';
    });
}
       $scope.init();


    $scope.errorMessages="";
    $scope.updateForgotPass = function (){
            $scope.changeRequest= {
                 newPass : $scope.newPass ,
                confirmPass: $scope.confirmPass
}
        console.log($scope.changeRequest);
        $http.post(`/api/updatePass/${$routeParams.token}`, $scope.changeRequest).then((resp) => {
            if (resp.status == 200) {
                Swal.fire({
                    icon: "success",
                    title: "Thành công!",
                    text: "Bạn hãy đăng nhập lại để tiếp tục sử dụng dịch vụ nhé!",
                    timer: 5600,
                });
                $window.location.href = '#login';
            }
        }).catch(error => {
            if (error.status == 400) {
            $scope.errorMessages = error.data.message;
        }
        });

    }


})
