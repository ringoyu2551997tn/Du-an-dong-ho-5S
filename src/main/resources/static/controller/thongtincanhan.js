const getThongTinCaNhanByIdAPI = "http://localhost:8080/khach-hang/thong-tin-ca-nhan/";
const putDiaChiAPI = "http://localhost:8080/api/dia-chi/update/";
const putDiaChiDefaultAPI = "http://localhost:8080/api/dia-chi/updateDefault/";
const getAllDiaChiAPI = "http://localhost:8080/api/dia-chi/find-all/";
const putUpdateThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin-ca-nhan/update/";
const deleteDiaChiAPI = "http://localhost:8080/api/dia-chi/delete/";
const addDiaChiAPI = "http://localhost:8080/api/dia-chi/them-dia-chi/";
class ThongTinCaNhan {
    constructor(id,tenKhachHang,soDienThoai,email,gioiTinh,diaChi,ngaySinh){
        this.id = id;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
    }
};


myApp.controller("ThongTinCaNhanController",
    function ($scope, $http, $window) {
        $scope.thongtincanhan = new ThongTinCaNhan();
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.getThongTinCaNhan = () => {
            if(currentUser != null) {
                $http
                    .get(getThongTinCaNhanByIdAPI + currentUser.idKhachHang)
                    .then((response) => {
                        $scope.thongtincanhan = response.data;
                        console.log(response)
                    })
                    .catch((error) => {
                        console.log(error);
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
        $scope.getThongTinCaNhan();


        $scope.error="";
        $scope.updateThongTinCaNhan = function(){
            if($scope.thongtincanhan.tenKhachHang == null){
                $scope.error ="* Vui lòng không để trống tên ";
                return;
            }
            if($scope.thongtincanhan.tenKhachHang.length < 5){
                $scope.error ="* Tên trên 5 ký tự ";
                return;
            }
            if($scope.thongtincanhan.soDienThoai == null){
                $scope.error ="* Vui lòng không để trống SĐT";
                return;
            }
            if(!$scope.thongtincanhan.soDienThoai.match(/^[0-9]*$/)){
                $scope.error ="* Số điện thoại là sô";
                return;
            }
            if($scope.thongtincanhan.soDienThoai.length <9  || $scope.thongtincanhan.soDienThoai.length > 10){
                $scope.error ="* Số điện thoại gồm 10 ký tự";
                return;
            }
            if($scope.thongtincanhan.ngaySinh == null){
                $scope.error ="* Vui lòng không để trống ngày sinh";
                return;
            }
            if($scope.thongtincanhan.ngaySinh > new Date()){
                $scope.error ="*Ngày sinh phải nhỏ chứ ";
                return;
            }
            if($scope.thongtincanhan.gioiTinh == null){
                $scope.error ="* Vui lòng không để trống giới tính";
                return;
            }
            console.log($scope.thongtincanhan)

            $http
                .put(putUpdateThongTinCaNhanAPI + currentUser.idKhachHang,$scope.thongtincanhan)
                .then((request) => {
                    Swal.fire({
                        icon: "success",
                        title: "Cập nhập thành công!",
                        showConfirmButton: true,
                        closeOnClickOutside: true,
                        timer: 5600,
                    });
                        $window.location.reload();
                })
                .catch((error) => {
                    console.log(error);
                });
        };

    });


myApp.controller("AddressCtrl",
    function ($scope, $http, $window) {

        $scope.diaChis =[];
        $scope.diaChi ={};
        $scope.tpMap =[];
        $scope.qhMap =[];
        $scope.pxMap=[];
        $scope.isFirstRunQH = 0;
        $scope.isFirstRunPX = 0;
        let currentUser = JSON.parse(localStorage.getItem("currentUser"));
        $scope.getAllDiaChi = () =>{
            if(currentUser != null){
                $http
                    .get(getAllDiaChiAPI + currentUser.idKhachHang)
                    .then((request) => {
                        $scope.diaChis = request.data;
                    })
                    .catch((error) => {
                        console.log(error);
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
        $scope.getAllDiaChi();

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
            ["thanhPho"],
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
            ["quanHuyen"],
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

        $scope.getdc = function (dc){
            $scope.diachi = {
                idDiaChi : dc.idDiaChi,
                idTT:dc.idTT,
                idQH: dc.idQuanHuyen,
                idPX: dc.idPhuongXa ,
                diachi: dc.diaChi,
                ghichu: dc.ghiChu ,
                sdt:dc.soDienThoai
            }
        }

        $scope.suaDC= function (diachi) {
            if(currentUser != null) {
                if( $scope.diachi.diachi == null){
                    $scope.error ="* Vui lòng không để trống địa chỉ ";
                    return;
                }

                if($scope.diachi.sdt == null){
                    $scope.error ="* Vui lòng không để trống SĐT";
                    return;
                }
                if(!$scope.diachi.sdt.match(/^[0-9]*$/)){
                    $scope.error ="* Số điện thoại là sô";
                    return;
                }
                if($scope.diachi.sdt.length <9  ||$scope.diachi.sdt.length > 10){
                    $scope.error ="* Số điện thoại gồm 9-10 ký tự";
                    return;
                }
                $scope.diachiRequest = {
                    idTinhThanh: $scope.thanhPho == null ? diachi.idTT : $scope.thanhPho,
                    idQuanHuyen: $scope.quanHuyen  == null ? diachi.idQH : $scope.quanHuyen,
                    idPhuongXa: $scope.phuongXa  == null ? diachi.idPX : $scope.phuongXa,
                    diaChi: $scope.diachi.diachi,
                    ghiChu: $scope.diachi.ghichu == null ? diachi.ghichu : $scope.diachi.ghichu,
                    soDienThoai: $scope.diachi.sdt
                }
                Swal.fire({
                    title: 'Loading',
                    onOpen: () => {
                        Swal.showLoading();
                    },
                    timer: 2000
                })
                setTimeout(function () {
                    $http
                        .put(putDiaChiAPI + diachi.idDiaChi, $scope.diachiRequest)
                        .then((request) => {
                            Swal.fire({
                                icon: "sucess",
                                title: "Cập nhập thành công!",
                                showConfirmButton: true,
                                closeOnClickOutside: true,
                                timer: 2000,
                            });
                            setTimeout(function (){
                                $window.location.reload();
                            },1800)
                        })
                        .catch((error) => {
                            console.log(error);
                        });
                },2000)

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
        }

        $scope.updateMacDinh = function (diachi){
            if(currentUser != null) {
                Swal.fire({
                    title: 'Bạn cập nhật địa chỉ mặc định ?',
                    text: "Cập nhạp địa chỉ hiện tại ",
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
                            timer: 2000
                        })
                        setTimeout(function () {
                            $http
                                .put(putDiaChiDefaultAPI + diachi.khachHang.idKhachHang + "/" + diachi.idDiaChi)
                                .then((request) => {
                                    Swal.fire({
                                        icon: "success",
                                        title: "Cập nhập thành công!",
                                        showConfirmButton: true,
                                        closeOnClickOutside: true,
                                        timer: 2000,
                                    });
                                    setTimeout(function (){
                                        $window.location.reload();
                                    },1800)
                                })
                                .catch((error) => {
                                    console.log(error);
                                });
                        },2000)
                    }
                })
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
        }

        $scope.xoa = function (dc){
            if(currentUser != null) {
                Swal.fire({
                    title: 'Bạn muốn xóa địa chỉ?',
                    text: "Xóa địa chỉ này khỏi thông tin của bạn",
                    icon: 'warning',
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
                            timer: 2000
                        })
                        setTimeout(function () {
                            $http
                                .delete(deleteDiaChiAPI + dc.idDiaChi)
                                .then((request) => {
                                    Swal.fire({
                                        icon: "success",
                                        title: "thông báo!",
                                        text: "Xóa thành công!",
                                        showConfirmButton: true,
                                        closeOnClickOutside: true,
                                        timer: 2000,
                                    });
                                    setTimeout(function () {
                                        $window.location.reload();
                                    }, 1800)
                                }).catch((error) => {
                                console.log(error);
                            })
                        },2000)
                    }
                })
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
                }

        $scope.resetData = function (){
            $scope.diachi = {
                idDiaChi :"",
                idTT:"",
                idQH: "",
                idPX: "" ,
                diachi:"",
                ghichu: "",
                sdt:""
            }
        }

        $scope.postThemDiaChi = () => {

            if(currentUser != null) {
                if( $scope.thanhPho== null){
                    $scope.error ="* Vui lòng không để trống thành phố ";
                    return;
                }
                if( $scope.quanHuyen== null){
                    $scope.error ="* Vui lòng không để trống quận huyện ";
                    return;
                }
                if( $scope.phuongXa== null){
                    $scope.error ="* Vui lòng không để trống phường xã";
                    return;
                }
                if( $scope.diachi.diachiChiTiet == null){
                    $scope.error ="* Vui lòng không để trống địa chỉ ";
                    return;
                }

                if($scope.diachi.sdt == null){
                    $scope.error ="* Vui lòng không để trống SĐT";
                    return;
                }
                if(!$scope.diachi.sdt.match(/^[0-9]*$/)){
                    $scope.error ="* Số điện thoại là sô";
                    return;
                }
                if($scope.diachi.sdt.length <9  ||$scope.diachi.sdt.length > 10){
                    $scope.error ="* Số điện thoại gồm 9-10 ký tự";
                    return;
                }
                $scope.diachiRequest = {
                    idTinhThanh: $scope.thanhPho,
                    idQuanHuyen:$scope.quanHuyen,
                    idPhuongXa: $scope.phuongXa,
                    diaChi: $scope.diachi.diachiChiTiet,
                    ghiChu: $scope.diachi.ghichu,
                    soDienThoai: $scope.diachi.sdt
                }

                        Swal.fire({
                            title: 'Loading',
                            onOpen: () => {
                                Swal.showLoading();
                            },
                            timer: 2000
                        })
                        setTimeout(function () {
                            $http
                                .post(addDiaChiAPI + currentUser.idKhachHang , $scope.diachiRequest)
                                .then((request) => {
                                    Swal.fire({
                                        icon: "success",
                                        title: "Thêm thành công!",
                                        showConfirmButton: true,
                                        closeOnClickOutside: true,
                                        timer: 2000,
                                    });
                                    setTimeout(function (){
                                        $window.location.reload();
                                    },1800)
                                })
                                .catch((error) => {
                                    console.log(error);
                                });
                        },2000)

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
    });
