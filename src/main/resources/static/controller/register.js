
myApp.controller("registerCtrl", function ($scope,$rootScope, $http,$location) {

    $scope.tpMap =[];
    $scope.qhMap =[];
    $scope.pxMap=[];
    $scope.isFirstRunQH = 0;
    $scope.isFirstRunPX = 0;
    // $scope.idTinhThanh ;
    $scope.getTP = ()=>{
        $scope.qhMap =[];
        $scope.pxMap=[];
        $http.get('/api/getTP')
            .then(resp =>{
                // $scope.tpMap.map
                $scope.tpMap = resp.data;
            }).catch(error =>{
            alert("Loi roi")
        })
    }
    $scope.getTP();

    $scope.$watchGroup(
        ["user.idTinhThanh"],
        function (newValues, oldValues) {
            $scope.pxMap =[];
            $scope.isFirstRunQH++;
            if ($scope.isFirstRunQH <= 1) {
                return;
            }
            $http.get("http://localhost:8080/api/getQH/"+ newValues)
                .then(resp =>{
                    $scope.qhMap = resp.data;
                }).catch(error =>{
            })
        }
    );


    $scope.$watchGroup(
        ["user.idQuanHuyen"],
        function (newValues, oldValues) {
            $scope.isFirstRunPX++;
            if ($scope.isFirstRunPX <= 1) {
                return;
            }
                $http.get("http://localhost:8080/api/getPX/" + newValues)
                    .then(resp => {
                        $scope.pxMap = resp.data;
                        console.log(resp)
                    }).catch(error => {
                })
        }
    );

    $scope.registerClient = function(){
        let user = angular.copy($scope.user);
        console.log(user)
        $http.post('/api/register',user)
            .then(resp =>{
                // resp.data.createDate= new Date(resp.data.createDate);
                // $scope.items.push(resp.data);
                // $scope.reset();
                Swal.fire({
                    icon: "warning",
                    title: "Đăng ký thành công!",
                    text: "Hãy đăng nhập để  mua hàng nhé!",
                    showConfirmButton: false,
                    closeOnClickOutside: false,
                    allowOutsideClick: false,
                    timer: 1600,
                });
                $location.path("/login")
            }).catch(error =>{
                   $scope.errorMessage = error.data.message
            // console.log(error)
            // alert("Loi roi")
        })
    }

})