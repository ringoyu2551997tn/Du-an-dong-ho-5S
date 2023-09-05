const themDonHangApi = "http://localhost:8080/api/don-hang/them-don-hang";
// const getThongTinCaNhanAPI = "http://localhost:8080/khach-hang/thong-tin/";
// const getDiaChiAPI = "http://localhost:8080/khach-hang/getDiaChi";
const getFeeAPI = "http://localhost:8080/api/don-hang/tinh-phi-van-chuyen";
const thanhToanVNPayAPI = "http://localhost:8080/api/don-hang/thanh-toan-vnpay";
const getTinhThanhAPI = "http://localhost:8080/api/dia-chi/get-tinh-thanh";
const getQuanHuyenAPI = "http://localhost:8080/api/dia-chi/get-quan-huyen/";
const getPhuongXaAPI = "http://localhost:8080/api/dia-chi/get-phuong-xa/";
// const getAllDiaChiAPI = "http://localhost:8080/api/dia-chi/find-all/";


myApp.controller(
  "ThanhToanCtrl",
  function ($scope, $rootScope, $http, checkOutDataService, $window,$location) {
    // if (new URLSearchParams(window.location.search).get("message") != null) {
    //   var mess = new URLSearchParams(window.location.search).get("message");
    //   alert("Lỗi" + mess);
    // }let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    if(currentUser==null){
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
    $scope.listHoaDonChiTietRequest = [];
    $scope.soLuongSanPham;
    $scope.listHoaDonChiTietRequest = checkOutDataService.getData();

    if ($scope.listHoaDonChiTietRequest == undefined) {
        Swal.fire({
            title: 'Cảnh báo',
            text: "Bạn chưa chọn sản phảm nào",
            icon: 'warning',
            timer:2000
        })
        setTimeout(function (){
            $window.location.href = '#cart';
            },1900)
      return;
    } else {
      $scope.listHoaDonChiTietRequest.forEach((item) => {
        $scope.soLuongSanPham = +item.soLuong;
      });
    }
    $scope.ghiChu;
    $scope.isVNPAY = false;
    $scope.fee;
    $scope.tongTien = 0;
    $scope.diaChiGiaoHang ={};
    $scope.diaChiGiaoHangs=[];
      $scope.tpMap =[];
      $scope.qhMap =[];
      $scope.pxMap=[];
      $scope.isFirstRunQH = 0;
      $scope.isFirstRunPX = 0;

    $scope.getThongTinCaNhan = () => {
        if(currentUser!= null) {
            $http
                .get(getAllDiaChiAPI + currentUser.idKhachHang)
                .then((response) => {
                    $scope.diaChiGiaoHangs = response.data;
                    $scope.diaChiGiaoHang = $scope.diaChiGiaoHangs.filter(function (
                        item
                    ) {
                        return item.trangThaiMacDinh == 1;
                    })[0];
                    console.log($scope.diaChiGiaoHang);
                    $scope.idDiaChi = $scope.diaChiGiaoHang.idDiaChi;
                    $scope.getFeeRequest = {
                        idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
                        idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
                        soLuongSanPham: $scope.soLuongSanPham,
                    };
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    };
    $scope.init = () => {
      $scope.getThongTinCaNhan();
    };
    $scope.init();
    $scope.getFee = () => {
      $http
        .post(getFeeAPI, $scope.getFeeRequest)
        .then((response) => {
          $scope.fee = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    };



    $scope.thanhToan = (isVNPAY) => {

      $scope.checkOutRequest = {
        khachHangId: currentUser.idKhachHang,
        listHoaDonChiTietRequest:
          $scope.listHoaDonChiTietRequest,
          idTinhThanh : $scope.diaChiGiaoHang.idTinhThanh,
        idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
        idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
        diaChi: $scope.diaChiGiaoHang.diaChi,
        ghiChu: $scope.ghiChu,
        soLuongSanPham: $scope.soLuongSanPham,
        phiVanChuyen: $scope.fee,
      };
      if (isVNPAY==true) {
          Swal.fire({
              title: 'Bạn muốn thanh toán VNPay?',
              text: "Thanh toán với phương thức VNPay ",
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

                  setTimeout(function (){
                      $http
                          .post(thanhToanVNPayAPI, $scope.checkOutRequest)
                          .then(function (response) {
                              var url = response.data.url;
                              $window.open(url, "_self");
                          })
                          .catch((error) => {
                              console.log(error);
                          });
                  },1900)
              }
          })

      } else {
          Swal.fire({
              title: 'Bạn muốn thanh toán trả sau?',
              text: "Thanh toán với phương thức trả sau khi nhận hàng ",
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

                  setTimeout(function (){
                      $http
                          .post(themDonHangApi, $scope.checkOutRequest)
                          .then((response) => {
                              if(response.status == 200){

                                  $window.location.href ="#success";
                              }else{
                                  $window.location.href="#fail";
                              }
                          })
                          .catch((error) => {
                              console.log(error);
                              $window.location.href="#fail";
                          });
                  },1900)
              }
          })
      }
    };
    $scope.listHoaDonChiTietRequest.forEach((item) => {
      $scope.tongTien = +Number(item.giaBan) * Number(item.soLuong);
    });


    $scope.$watch("diaChiGiaoHang", (newValue, oldValue) => {
      $scope.getFeeRequest = {
        idQuanHuyen: $scope.diaChiGiaoHang.idQuanHuyen,
        idPhuongXa: $scope.diaChiGiaoHang.idPhuongXa,
        soLuongSanPham: $scope.soLuongSanPham,
      };
      $scope.getFee();
    });

    $scope.getListThanhPho = () =>{
        $http
            .get(getTinhThanhAPI)
            .then(function (response) {
                $scope.listThanhPho = new Map();
                for (const key in response.data) {
                    $scope.listThanhPho.set(key, response.data.key);
                }
            })
            .catch(function (error){
                console.log(error);
            })

    }

      $scope.getListThanhPho();

    $scope.changeDC = (dc)=>{
        $scope.diaChiGiaoHang = dc ;
    }
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

      $scope.themDiaChi =()=>{
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
                  .then((resp) => {
                      $scope.diaChiGiaoHang = resp.data;
                      $scope.getAllDC();
                      Swal.fire({
                          icon: "success",
                          title: "Thêm thành công!",
                          showConfirmButton: true,
                          closeOnClickOutside: true,
                          timer: 8600,
                      });

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

      $scope.getAllDC = () =>{
          $http
              .get(getAllDiaChiAPI + currentUser.idKhachHang)
              .then((response)=>{
                  $scope.diaChiGiaoHangs = response.data;
          }).catch((e)=>{

          })
      }
  }
);
