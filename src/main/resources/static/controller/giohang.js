    myApp.controller("cartCtrl", function ($scope,$rootScope, $http,$window,checkOutDataService,$location) {
    $scope.cart = [];
    $scope.total = 0;
    $scope.totalSp = 0;
    $scope.totalnavBar = 0;
    $scope.totalSpnavBar  = 0;
    $scope.selection=[];
    $scope.SeriBySP = new Map();
    $scope.errorSelectedSP;
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    //load cart
    $rootScope.index = function () {
        $scope.total = 0;
        $scope.totalSp = 0;
        if(currentUser != null) {
            $http.get(`/api/giohang/${currentUser.idKhachHang}`).then((resp) => {
                $scope.cart = resp.data;
                $scope.cart.forEach(item => {
                    $scope.countSeri(item.chiTietSanPham.idChiTietSanPham)
                })
            }).catch(error => {
                if(error.status == 403) {
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
    $rootScope.index();


    // toorng sp vaf toorng tieefn
    $scope.setTotalnavBar  = function (item) {

        if (item) {

            $scope.totalnavBar  += item.giaBan * item.soLuongSanPham;
            $scope.totalSpnavBar  +=  item.soLuongSanPham;

        }
    };
    $scope.setTotals = function (item) {

        if (item) {
            $scope.total += item.giaBan * item.soLuongSanPham;
            $scope.totalSp +=  item.soLuongSanPham;


        }
    };




    // api update soLuongtronggiohang
    $scope.update = function (cart, soLuong){
            $http.put(`/api/giohang/update/${cart.idChiTietGioHang}?soLuong=${soLuong}`)
                .then(resp => {
                    // $scope.cart = resp.data;
                    // alert("cap nhap thanh cong");
                    console.log(resp)
                })
                .catch(error => {
                    alert("Loi roi", error);
                })
        // }else{
        //     Swal.fire({
        //         icon: "warning",
        //         title: "Bạn chưa đăng nhập !",
        //         text: "Hãy đăng nhập để tiếp tục shopping!",
        //         timer: 1600,
        //     });
        // }
    }


// giam so luong
    $scope.giam = function (item, soLuong) {
                if (item.soLuongSanPham <= 1) {
                    Swal.fire({
                        title: 'Bạn muốn xóa ?',
                        text: "Xóa sản phẩm này khỏi giỏ hàng",
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Xác nhận'
                    }).then((result) => {
                        if (result.isConfirmed) {
                            Swal.fire({
                                title:'Loading',
                                onOpen: ()=>{
                                    Swal.showLoading();
                                },
                                timer : 2000
                            })

                            setTimeout(function (){
                                $scope.delete(item);
                                var sp = $scope.selection.find((e) => e === item)
                                if(sp){
                                    $scope.total -= item.giaBan * item.soLuongSanPham ;
                                    $scope.totalSp -= item.soLuongSanPham ;
                                }
                                $window.location.reload();
                                return;
                            },2600)

                        }else {
                            item.soLuongSanPham = Number(item.soLuongSanPham) + 1;
                        }
                    })
                }else {
                    item.soLuongSanPham = Number(item.soLuongSanPham) - 1;
                    soLuong = item.soLuongSanPham;
                    $scope.update(item, soLuong);
                    var sp = $scope.selection.find((e) => e === item)
                    if (sp) {
                        $scope.total -= item.giaBan;
                        $scope.totalSp--;
                    }
                }
    };
    $scope.countSeri = function (idChiTietSanPham){
            $http
                .get(`/chi-tiet-san-pham/countSeri/${idChiTietSanPham}`)
                .then(function (response) {
                    $scope.SeriBySP.set(idChiTietSanPham, response.data);
                    // console.log(response.data,"daaaaaaaaaaaa")
                    // $scope.count = response.data;
                })

                .catch(function (error) {
                    console.log(error);
                });
        }
    $scope.tang = function (item,soLuong) {

        if (item) {
            if(item.soLuongSanPham >= $scope.SeriBySP.get(item.chiTietSanPham.idChiTietSanPham)){
                Swal.fire({
                            icon: "warning",
                            title: "Thông báo!",
                            text: "Số lượng có giới hạn ",
                            timer: 2600,
                        });
                return;
            }else {
                item.soLuongSanPham = Number(item.soLuongSanPham) + 1;
                soLuong = item.soLuongSanPham;
                $scope.update(item, soLuong);
                var sp = $scope.selection.find((e) => e === item)
                if (sp) {
                    $scope.total += item.giaBan;
                    $scope.totalSp++;
                }
            }

        }
    };

    //api xóa giohang
    $scope.delete = function (item){
        $http.delete(`/api/giohang/delete/${item.idChiTietGioHang}`)
            .then(resp =>{
                const index = $scope.cart.findIndex(p => p.idChiTietGioHang ==  item.idChiTietGioHang);
                $scope.cart.splice(index,1);

            })
            .catch(error =>{
                alert("Loi roi");
                console.log("eror"+ error);
            })
    }

    //api xóa giohang
    $scope.deleteAll = function (){
        $http.delete(`/api/giohang/deleteAll`)
            .then(resp =>{
                $scope.cart= [];
                // alert("xoa thanh cong");
                setTimeout(function (){
                    $window.location.reload();
                },1600)
            })
            .catch(error =>{
                alert("Loi roi");
                console.log("eror"+ error);
            })
    }

    //xóa sanpham trong giỏ hàng
    $scope.removeSP= function (item) {
        if (item) {
            Swal.fire({
                title: 'Bạn muốn xóa ?',
                text: "Xóa sản phẩm này khỏi giỏ hàng",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xác nhận'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title:'Loading',
                        onOpen: ()=>{
                            Swal.showLoading();
                        },
                        timer : 2000
                    })

                    setTimeout(function (){
                        $scope.delete(item);
                        var sp = $scope.selection.find((e) => e === item)
                        if(sp){
                            $scope.total -= item.giaBan * item.soLuongSanPham ;
                            $scope.totalSp -= item.soLuongSanPham ;
                        }
                            $window.location.reload();
                    },1900)
                }
            })

        }
    };

    $scope.removeAll = function () {
        if ($scope.cart) {
            $scope.deleteAll($scope.cart);
            $scope.total = 0;
            $scope.totalSp = 0;

            // console.log($scope.cart);
        }
    };

    $scope.changeSL = function (item) {
        if (item) {
            if (item.soLuongSanPham < 1) {
                $scope.removeSP(item);
                return;
            }
            $scope.update(item);
            $scope.setTotals(item)
        }
    };
    $scope.toggleSelection = function toggleSelection(item) {
        var idx = $scope.selection.indexOf(item);
        // is currently selected
        if (idx > -1) {
            $scope.selection.splice(idx, 1);
            $scope.total -= item.giaBan * item.soLuongSanPham;
            $scope.totalSp -=  item.soLuongSanPham;

        }
        // is newly selected
        else {
            $scope.selection.push(item);
            $scope.setTotals(item);
        }

    };
        $scope.buycart = () => {
            $scope.chiTietSanPham=[];
            $scope.cart.forEach(item=>{
                $scope.chiTietSanPham.push({
                    idChiTietSanPham: item.chiTietSanPham.idChiTietSanPham,
                    giaBan: item.giaBan,
                    soLuong: item.soLuongSanPham,
                    chiTietSanPham: item.chiTietSanPham
                })
            })
            checkOutDataService.setData($scope.chiTietSanPham);

            Swal.fire({
                title: 'Xác nhận thanh toán?',
                text: "Bạn hãy xác nhận thanh toán",
                icon: 'info',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xác nhận'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title:'Loading',
                        onOpen: ()=>{
                            Swal.showLoading();
                        },
                        timer : 2000
                    })
                    setTimeout(function () {
                        $window.location.href = "#checkout"
                    }, 1900)
                }
            })

        };

        $scope.buy = () => {

            $scope.chiTietSanPham=[];
            if($scope.selection.length == 0){
                $scope.errorSelectedSP = "* Vui lòng chọn sản phẩm!";
                return;
            }
            $scope.selection.forEach(item=>{
                $scope.chiTietSanPham.push({
                    idChiTietSanPham: item.chiTietSanPham.idChiTietSanPham,
                    giaBan: item.giaBan,
                    soLuong: item.soLuongSanPham,
                    chiTietSanPham: item.chiTietSanPham
                })
            })
            checkOutDataService.setData($scope.chiTietSanPham);

            Swal.fire({
                title: 'Xác nhận thanh toán?',
                text: "Bạn hãy xác nhận thanh toán",
                icon: 'info',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Xác nhận'
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: 'Loading',
                        onOpen: () => {
                            Swal.showLoading();
                        },
                        timer: 1000
                    })
                    setTimeout(function () {
                        $window.location.href = "#checkout"
                    }, 1900)
                }
            })

      };



})