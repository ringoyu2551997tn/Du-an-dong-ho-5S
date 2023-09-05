myApp.controller(
    "historyCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
        $scope.loading = true;

           if (currentUser != null) {
                $http.get(`/api/don-hang/findAll/${currentUser.idKhachHang}`)
                    .then((resp) => {
                        $scope.donHang = resp.data;
                        $scope.items.push($scope.donHang[0])
                        $scope.items.push($scope.donHang[1])
                    }).catch(error => {
                    if (error.status == 403) {
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
                });
            } else {
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
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();

    })

myApp.controller(
    "historyChoCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
        $scope.loading = true;
            if (currentUser != null) {
                $http.get(`/api/don-hang/findByStatus/${currentUser.idKhachHang}?status=0`)
                    .then((resp) => {
                        $scope.donHang = resp.data;
                        $scope.items.push($scope.donHang[0])
                    }).catch(error => {
                    if (error.status == 403) {
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
                });
            } else {
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

            $scope.huy =function (item){
               if($scope.lyDo == null && $scope.lyDoKhac == null){
                   $scope.error ="Lý do không được để trống "
               }else {
                   var donHangRequest = {
                       idDonHang: item.idDonHang,
                       lyDo: $scope.lyDo == null ? $scope.lyDoKhac : $scope.lyDo,
                       trangThaiDonHang: 4
                   }
                   console.log(donHangRequest)
                   $http.put(`/api/don-hang/update`,donHangRequest)
                       .then((resp) => {
                           $scope.donHang = resp.data;

                           // hoaDonChiTiets = $scope.donHang.hoaDonChiTiets;
                           // hoaDonChiTiets.forEach(item =>{
                           //     $scope.updateCTSP(item.)
                           // })
                           $('#myModal').modal('hide');
                          $window.location.href='/index#/history/4';
                           $window.location.reload();
                       }).catch(error => {
                       console.log(error)
                   });

               }
            }
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();


    })

myApp.controller(
    "historyWaitCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
        $scope.loading = true;
        if (currentUser != null) {
            $http.get(`/api/don-hang/findByStatus/${currentUser.idKhachHang}?status=1`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    $scope.items.push($scope.donHang[0])
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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

        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();
    })

myApp.controller(
    "historyShippingCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
        $scope.loading = true;
        if (currentUser != null) {
            $http.get(`/api/don-hang/findByStatus/${currentUser.idKhachHang}?status=2`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    $scope.items.push($scope.donHang[0])
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();
    })

myApp.controller(
    "historyDoneCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        const today = new Date();
// // Trừ đi 7 ngày
//         today.setDate(today.getDate() - 7);
        today.setMinutes(today.getMinutes() - 2);
        $rootScope.expirationDate = today.toISOString();
        console.log( $rootScope.expirationDate)

        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
        $scope.loading = true;
        $scope.isPhanHoi;
        $scope.checkPhanHoi = new Map();
        if (currentUser != null) {
            $http.get(`/api/don-hang/findByStatus/${currentUser.idKhachHang}?status=3`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    $scope.items.push($scope.donHang[0])
                    $scope.items.push($scope.donHang[1])
                    $scope.donHang.forEach(item => {
                            item.hoaDonChiTiets.forEach(h => {
                                $scope.checkPhanHoiAPI(h.chiTietSanPham.idChiTietSanPham)
                            })
                        }
                    )
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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

        $scope.hoanTra = function (item){
                if($scope.lyDo == null && $scope.lyDoKhac == null){
                    $scope.error ="Lý do không được để trống "
                }else {
                    var donHangRequest = {
                        idDonHang: item.idDonHang,
                        lyDo: $scope.lyDo == null ? $scope.lyDoKhac : $scope.lyDo,
                        trangThaiDonHang: 5
                    }
                    console.log(donHangRequest)
                    $http.put(`/api/don-hang/update`,donHangRequest)
                        .then((resp) => {
                            $scope.donHang = resp.data;
                            $('#myModal').modal('hide');
                            $window.location.href='/index#/history/5';
                            $window.location.reload();
                        }).catch(error => {
                        console.log(error)
                    });

                }
        }

        $scope.rating = 0;
        $scope.ratings = {
            current: -1,
            max: 5,
        };



        $scope.checkPhanHoiAPI = function (idChiTietSanPham) {
            if(currentUser) {
                $http
                    .get(
                        `phan-hoi/checkPhanHoi?idKhachHang=${currentUser.idKhachHang}&idSanPham=${idChiTietSanPham}`
                    )
                    .then(function (response) {
                        $scope.isPhanHoi= response.data;
                        $scope.checkPhanHoi.set(idChiTietSanPham,$scope.isPhanHoi);
                    })
                    .catch(function (error) {
                        console.log(error);
                    });

            }else{
                return true;
            }
        };
        $scope.sendRate = function (h) {
            if(currentUser) {
                $scope.phanhoirequest = {
                    danhGia: $scope.ratings.current,
                    noiDungPhanHoi:$scope.noiDungPhanHoi,
                    idChiTietSanPham: h.chiTietSanPham.idChiTietSanPham,
                    idKhachHang: currentUser.idKhachHang,
                };
                console.log($scope.phanhoirequest);
                $http
                    .post(`/api/phan-hoi/add`, $scope.phanhoirequest)
                    .then((resp) => {
                        console.log(resp);
                        $scope.isPhanHoi = true;
                        Swal.fire({
                            icon: "success",
                            title: "Thành công!",
                            text: "Đánh giá thành công!",
                            showConfirmButton: true,
                            closeOnClickOutside: true,
                            timer: 3600,
                        });
                        setTimeout(function (){
                            $window.location.reload();
                        },2600)

                    })
                    .catch((error) => {
                        alert("Loi roi", error);
                    });
            }else{
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
        };
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();
    })


myApp.controller(
    "historyCancelCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
        $scope.loading = true;
        if (currentUser != null) {
            $http.get(`/api/don-hang/findByStatus/${currentUser.idKhachHang}?status=4`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    $scope.items.push($scope.donHang[0])
                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();
    })

myApp.controller(
    "historyReturnCtrl",
    function (  $scope, $rootScope, $http,$routeParams,$location, $window
    ) {

        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.donHang = [];
        $scope.items= [];
            $scope.loading = true;
        if (currentUser != null) {
            $http.get(`/api/don-hang/findByStatus/${currentUser.idKhachHang}?status=5`)
                .then((resp) => {
                    $scope.donHang = resp.data;
                    $scope.items.push($scope.donHang[0])

                }).catch(error => {
                if (error.status == 403) {
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
            });
        } else {
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
        var i =2;
        $scope.more = function (){
            if(i == $scope.donHang.length){
                $scope.loading = false;
            }

            for( i; i < $scope.donHang.length;i++ ){
                $scope.newItem = $scope.donHang[i++];
                i = i++;
                $scope.items.push( $scope.newItem );
                console.log($scope.items)
                break;
            }

        }
        $scope.more();
    })

myApp.directive("whenScrolled", function(){
    return{

        restrict: 'A',
        link: function($scope, elem, attrs){

            // we get a list of elements of size 1 and need the first element
           var raw = elem[0];

            // we load more elements when scrolled past a limit
            elem.bind("scroll", function(){
                if(raw.scrollTop+raw.offsetHeight+5 >= raw.scrollHeight){
                    $scope.loading = true;
                    console.log(raw.scrollTop+raw.offsetHeight+5 >= raw.scrollHeight,"raw")
                    // we can give any function which loads more elements into the list
                    $scope.$apply(attrs.whenScrolled);
                }else {
                    $scope.loading = false;
                }
            });
        }
    }
});
myApp.directive("starRatings", function () {
    return {
        restrict: "A",
        template:
            '<ul class="rating">' +
            '<li ng-repeat="star in stars" ng-class="star"   ng-click="toggle($index)">' +
            "\u2605" +
            "</li>" +
            "</ul>",
        scope: {
            ratingValue: "=",
            max: "=",
            onRatingSelected: "&",
        },
        link: function (scope, elem, attrs) {
            var updateStars = function () {
                scope.stars = [];
                for (var i = 0; i < scope.max; i++) {
                    scope.stars.push({
                        filled: i < scope.ratingValue,
                    });
                    // elem[i].css("color","yellow")
                }
            };

            scope.toggle = function (index) {
                scope.ratingValue = index + 1;
                scope.onRatingSelected({
                    rating: index + 1,
                });
            };

            scope.$watch("ratingValue", function (oldVal, newVal) {
                if (newVal) {
                    updateStars();
                }
            });
        },
    };
});
