const detailSanPhamAPI =
  "http://localhost:8080/san-pham/san-pham-detail/id-san-pham=";
const getPhanHoiAPI = "http://localhost:8080/phan-hoi/get/idSanPham=";
const getSanPhamCungThuongHieuAPI = "http://localhost:8080/san-pham/cung-thuong-hieu=";
myApp.controller(
  "TrangChiTietSanPhamController",
  function (
    $scope,
    $rootScope,
    $http,
    $routeParams,
    checkOutDataService,
    $location,
    $window
  ) {
    $scope.idSp = $routeParams.idSp;
    $scope.chiTietSanPham;
    $scope.sanPhamDetail;
    $scope.isFirstRun = 0;
    $scope.soLuong = 1;
    $scope.moreInfo="active";
    $scope.dataSheet="";
    $scope.review="";

    $scope.phanHoi = [];
    $scope.pageSize = 2;
    $scope.currentPage = 1;
    $scope.maxPagesToShow = 2;
    $scope.totalPages;
    $scope.check;
    $scope.Items =[];
    $scope.spCungThuongHieu = [];
    $rootScope.currentDate = new Date().toISOString();
    $scope.selectImage = "";
    $scope.SeriBySP = new Map();
    var setDayDeo = new Set();
    var setVatLieu = new Set();
    var setMauSac = new Set();
    var setKichCo = new Set();

    $scope.setAvaiableVatLieu = new Set();
    $scope.setAvaiableMauSac = new Set();
    $scope.setAvaiableKichCo = new Set();

    $scope.selectedDD;
    $scope.selectedMS;
    $scope.selectedVL;
    $scope.selectedKC;

    $scope.getDetailSanPham = function (idSp) {
      $http
        .get(detailSanPhamAPI + idSp)
        .then(function (response) {
          $scope.sanPhamDetail = response.data;
          $scope.chiTietSanPham = $scope.sanPhamDetail.listChiTietSanPham[0];
          $scope.selectedDD = $scope.chiTietSanPham.dayDeo.tenDayDeo;
          $scope.selectedMS = $scope.chiTietSanPham.mauSac.tenMauSac;
          $scope.selectedVL = $scope.chiTietSanPham.vatLieu.tenVatLieu;
          $scope.selectedKC = $scope.chiTietSanPham.kichCo.tenKichCo;
          getSettingAttributeSp($scope.sanPhamDetail.listChiTietSanPham);
          $scope.PhanHoiAPI();
          $scope.countSeri();
          $rootScope.currentDate = new Date().toISOString();
          $scope.selectImage = $scope.sanPhamDetail.listAnhSanPham[0].link;
          getAvailabelAttribute(
            $scope.selectedDD,
            $scope.selectedMS,
            $scope.selectedVL,
            $scope.selectedKC
          );
        })
        .catch(function (error) {
          console.log(error);
        });
    };

    $scope.changeImage =function (item){
      $scope.selectImage = item.link;
    }
    $scope.tang = function () {
      $scope.soLuong = $scope.soLuong + 1;
    };
    $scope.giam = function () {
      if ($scope.soLuong == 1) {
        alert("Đã giảm tối thiểu");
        return;
      }
      $scope.soLuong = $scope.soLuong - 1;
    };

    $scope.getCungThuongHieu = function (idSanPham){
      $http
          .get(getSanPhamCungThuongHieuAPI + idSanPham)
          .then(function (response) {
            console.log(response.data);
            $scope.spCungThuongHieu = response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
    }

    $scope.getDetailSanPham($scope.idSp);
    var getSettingAttributeSp = function (listChiTietSanPham) {
      listChiTietSanPham.forEach((ctsp) => {
        setDayDeo.add(ctsp.dayDeo.tenDayDeo);
        setVatLieu.add(ctsp.vatLieu.tenVatLieu);
        setMauSac.add(ctsp.mauSac.tenMauSac);
        setKichCo.add(ctsp.kichCo.tenKichCo);
      });
      $scope.listDayDeo = Array.from(setDayDeo);
      $scope.listVatLieu = Array.from(setVatLieu);
      $scope.listMauSac = Array.from(setMauSac);
      $scope.listKichCo = Array.from(setKichCo);
    };
    $scope.getCungThuongHieu($scope.idSp);
    $scope.$watchGroup(
      ["selectedDD", "selectedVL", "selectedMS", "selectedKC"],
      function (newValues, oldValues) {
        $scope.soLuong = 1;
        $scope.isFirstRun++;
        if ($scope.isFirstRun <= 1) {
          return;
        }
        // $scope.selectedDD = newValues[0];
        // $scope.selectedVL = newValues[1];
        // $scope.selectedMS = newValues[2];
        // $scope.selectedKC = newValues[3];
        //DayDeo
        if (newValues[0] !== oldValues[0]) {
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return item.dayDeo.tenDayDeo == $scope.selectedDD;
            })[0];
            resetSelectedValue();
        }
        //VatLieu
        if (newValues[1] !== oldValues[1]) {
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return (
                item.dayDeo.tenDayDeo == $scope.selectedDD &&
                item.vatLieu.tenVatLieu == $scope.selectedVL
              );
            })[0];
            resetSelectedValue();
        }
        //MauSac
        if (newValues[2] !== oldValues[2]) {
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return (
                item.dayDeo.tenDayDeo == $scope.selectedDD &&
                item.vatLieu.tenVatLieu == $scope.selectedVL &&
                item.mauSac.tenMauSac == $scope.selectedMS
              );
            })[0];
            resetSelectedValue();
        }
        //KichCo
        if (newValues[3] !== oldValues[3]) {
          $scope.chiTietSanPham =
            $scope.sanPhamDetail.listChiTietSanPham.filter(function (item) {
              return (
                item.dayDeo.tenDayDeo == $scope.selectedDD &&
                item.vatLieu.tenVatLieu == $scope.selectedVL &&
                item.kichCo.tenKichCo == $scope.selectedKC &&
                item.mauSac.tenMauSac == $scope.selectedMS
              );
            })[0];
            resetSelectedValue();
        }
        //
        getAvailabelAttribute();
        $rootScope.currentDate = new Date().toISOString();
        $scope.PhanHoiAPI();
        $scope.countSeri();



      }
    );

    var resetSelectedValue = function () {
      $scope.selectedDD = $scope.chiTietSanPham.dayDeo.tenDayDeo;
      $scope.selectedMS = $scope.chiTietSanPham.mauSac.tenMauSac;
      $scope.selectedVL = $scope.chiTietSanPham.vatLieu.tenVatLieu;
      $scope.selectedKC = $scope.chiTietSanPham.kichCo.tenKichCo;
    };

    var getAvailabelAttribute = function () {
      $scope.setAvaiableVatLieu = new Set();
      $scope.setAvaiableMauSac = new Set();
      $scope.setAvaiableKichCo = new Set();

      //Filter Set vat lieu
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return item.dayDeo.tenDayDeo == $scope.selectedDD;
        })
        .forEach(function (item) {
          $scope.setAvaiableVatLieu.add(item.vatLieu.tenVatLieu);
        });
      // Filter Set Mau Sac
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return (
            item.dayDeo.tenDayDeo == $scope.selectedDD &&
            item.vatLieu.tenVatLieu == $scope.selectedVL
          );
        })
        .forEach(function (item) {
          $scope.setAvaiableMauSac.add(item.mauSac.tenMauSac);
        });
      //Filter Set Kich Co
      $scope.sanPhamDetail.listChiTietSanPham
        .filter(function (item) {
          return (
            item.dayDeo.tenDayDeo == $scope.selectedDD &&
            item.vatLieu.tenVatLieu == $scope.selectedVL &&
            item.mauSac.tenMauSac == $scope.selectedMS
          );
        })
        .forEach(function (item) {
          $scope.setAvaiableKichCo.add(item.kichCo.tenKichCo);
        });
    };
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    var giaSanPham= 0;
    $scope.getGia = function (){
      if($scope.chiTietSanPham.khuyenMai == null) {
        return giaSanPham = $scope.chiTietSanPham.giaSanPham;
      }else{
         if($scope.chiTietSanPham.khuyenMai.enabled == false ){
               return giaSanPham = $scope.chiTietSanPham.giaSanPham ;
           }else{
           if($rootScope.currentDate < $scope.chiTietSanPham.khuyenMai.ngayKetThuc.toString()){
              if($rootScope.currentDate < $scope.chiTietSanPham.khuyenMai.ngayBatDau.toString()){
                return giaSanPham = $scope.chiTietSanPham.giaSanPham ;
              }else {
                return giaSanPham =$scope.chiTietSanPham.giaSanPham - $scope.chiTietSanPham.giaSanPham * $scope.chiTietSanPham.khuyenMai.chietKhau/100;
              }
           }else{
             return giaSanPham = $scope.chiTietSanPham.giaSanPham ;


           }
         }
      }
    }

    $scope.changeTab = function (tab){

          $scope.moreInfo="";
      $scope.dataSheet="";
      $scope.review="";
      if(tab==1){
        $scope.moreInfo="active";
      }else if(tab==2){
        $scope.dataSheet="active";
      }else{
        $scope.review="active";
        $scope.PhanHoiAPI();

      }

    }

    $scope.$watchGroup(["phanHoi"], function () {
      $scope.currentPage=1;
      $scope.pages = [];
      var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
      var endPage = Math.min(
          $scope.totalPages,
          $scope.currentPage + $scope.maxPagesToShow
      );
      for (var i = startPage; i <= endPage; i++) {
        $scope.pages.push(i);
      }
      var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
      var endIndex = startIndex + $scope.pageSize;
      $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);


    });
    $scope.addToCart = function () {
      if(currentUser) {
        var item = {
          idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          soLuong: $scope.soLuong,
          idKhachHang: currentUser.idKhachHang,
          giaSanPham: $scope.getGia()
        };

        if ($scope.chiTietSanPham) {
          $http
              .post(`/api/giohang/addToCart`, item)
              .then((resp) => {
                // console.log(resp,"resssssssssssssssss")
                if (resp.data == '') {
                  Swal.fire({
                    icon: "warning",
                    title: "Thông báo !",
                    text: "Quá số lượng sản phẩm!",
                    timer: 5600,
                  });
                  return;
                } else {
                  Swal.fire({
                    icon: "success",
                    title: "Thành công",
                    text: "Đã thêm vào giỏ hàng!",
                    timer: 3600,
                  });
                  setTimeout(function (){
                    $window.location.reload();
                  },2600)
                }
              })
              .catch((error) => {
                alert("Loi roi", error);
              });
        } else {
          alert("Khong có sp");
          return;
        }
      }else{
        Swal.fire({
          icon: "warning",
          title: "Chưa đăng nhập",
          text: "Bạn hãy đăng nhập !",
          timer: 5600,
        });
        $window.location.href = '#login';
      }
    };
    $scope.buyNow = () => {
      checkOutDataService.setData([
        {
          // idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          giaBan: $scope.getGia(),
          idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          soLuong: $scope.soLuong,
          chiTietSanPham: $scope.chiTietSanPham
        },
      ]);
      Swal.fire({
        title: 'Xác nhận mua sản phẩm ?',
        text: "Bạn hãy xác nhận mua sản phẩm ",
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
          $window.location.href="#checkout"
          },1900)
        }
      })


    };

    $scope.PhanHoiAPI = function () {
      var idChiTietSanPham = $scope.chiTietSanPham.idChiTietSanPham;

      // const idSP = $scope.idSP;
      $http
        .get(`phan-hoi/get/${idChiTietSanPham}`)
        .then(function (response) {
          $scope.phanHoi = response.data;
          $scope.totalPages = Math.ceil(
            $scope.phanHoi.length / $scope.pageSize
          );
        })

        .catch(function (error) {
          console.log(error);
        });
    };

    $scope.changePage = function (page) {
      $scope.pages = [];
      $scope.currentPage = page;
      var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
      var endPage = Math.min(
          $scope.totalPages,
          $scope.currentPage + $scope.maxPagesToShow
      );
      var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
      var endIndex = startIndex + $scope.pageSize;
      for (var i = startPage; i <= endPage; i++) {
        $scope.pages.push(i);
      }
      $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
      $scope.checkFirstLastPage();
    };

    $scope.previousPage = function () {
      $scope.pages = [];
      if ($scope.currentPage > 1) {
        $scope.currentPage--;
        var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
        var endPage = Math.min(
            $scope.totalPages,
            $scope.currentPage + $scope.maxPagesToShow
        );
        var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
        var endIndex = startIndex + $scope.pageSize;
        for (var i = startPage; i <= endPage; i++) {
          $scope.pages.push(i);
        }
        $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
        $scope.checkFirstLastPage();
      }
    };

    $scope.nextPage = function () {
      $scope.pages = [];
      if ($scope.currentPage < $scope.totalPages) {
        $scope.currentPage++;
        var startPage = Math.max(1, $scope.currentPage - $scope.maxPagesToShow);
        var endPage = Math.min(
            $scope.totalPages,
            $scope.currentPage + $scope.maxPagesToShow
        );
        var startIndex = ($scope.currentPage - 1) * $scope.pageSize;
        var endIndex = startIndex + $scope.pageSize;
        for (var i = startPage; i <= endPage; i++) {
          $scope.pages.push(i);
        }
        $scope.Items = $scope.phanHoi.slice(startIndex, endIndex);
        $scope.checkFirstLastPage();
      }
    };

    $scope.checkFirstLastPage = function (){
      console.log($scope.currentPage);
      if($scope.currentPage<=1){
        $scope.isFirstPage= true;
      }else{
        $scope.isFirstPage = false;
      }
      if($scope.currentPage >= $scope.totalPages){
        $scope.isLastPage = true;
      }else{
        $scope.isLastPage=false;
      }
    }

  $scope.countSeri = function (){
    var idChiTietSanPham = $scope.chiTietSanPham.idChiTietSanPham;
    $http
        .get(`/chi-tiet-san-pham/countSeri/${idChiTietSanPham}`)
        .then(function (response) {
          console.log(response.data,"daaaaaaaaaaaaa")
          $scope.SeriBySP.set(idChiTietSanPham, response.data);
        })

        .catch(function (error) {
          console.log(error);
        });
  }





    $scope.rating = 0;
    $scope.ratings = {
      current: -1,
      max: 5,
    };
    $scope.sendRate = function () {
      if(currentUser) {
        $scope.phanhoirequest = {
          danhGia: $scope.ratings.current,
          noiDungPhanHoi: angular.element("#noiDungPhanHoi").val(),
          idChiTietSanPham: $scope.chiTietSanPham.idChiTietSanPham,
          idKhachHang: currentUser.idKhachHang,
        };
        console.log($scope.phanhoirequest);
        $http
            .post(`/api/phan-hoi/add`, $scope.phanhoirequest)
            .then((resp) => {
              console.log(resp);
              alert("Them thanh cong");
              $scope.PhanHoiAPI();
              // $scope.checkPhanHoiAPI();

              // $window.location.reload();
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
  }

);


myApp.directive("starRating", function () {
  return {
    template:
      '<ul class="rating" >' +
      '<li ng-repeat="star in stars" ng-class="star">' +
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
        }
      };
      scope.$watch("ratingValue", function (newVal) {
        if (newVal) {
          updateStars();
        }
      });
    },
  };
});

