const settingAPI = "http://localhost:8080/san-pham/get-setting";
const searchAPI = "http://localhost:8080/san-pham/tim-kiem";

myApp.controller("SanPhamController", function ($scope, $rootScope, $http,$filter,checkSearchService) {
  $scope.listSetting = {};
  $scope.listSanPham = [];
  $scope.danhMucIds = [];
  $scope.thuongHieuIds = [];
  $scope.dayDeoIds = [];
  $scope.vatLieuIds = [];
  $scope.kichCoIds = [];
  $scope.mauSacIds = [];
  $scope.tenSanPham;
  $scope.pageSize = 8;
  $scope.currentPage = 1;
  $scope.maxPagesToShow = 1;
  $scope.totalPages;
  $scope.isFirstPage = true;
  $scope.isLastPage=false;
  $scope.selectedSortOption = 0;
  $scope.price;
  $scope.searchServiceData= checkSearchService.getData();
  $scope.itemWithGiaNN = new Map();
  $scope.itemWithGiaLN = new Map()
  $rootScope.currentDate = new Date().toISOString();

  $scope.changeDanhMucs = function () {
    $scope.selectedDanhMucs = $filter('filter')($scope.listSetting.listDanhMuc, {checked: true});
  }
  $scope.changeThuongHieucs = function () {
    $scope.selectedThuongHieus = $filter('filter')($scope.listSetting.listThuongHieu, {checked: true});
  }
  $scope.changeDayDeos = function () {
    $scope.selectedDayDeos = $filter('filter')($scope.listSetting.listDayDeo, {checked: true});
  }
  $scope.changeVatLieus = function () {
    $scope.selectedVatLieus = $filter('filter')($scope.listSetting.listVatLieu, {checked: true});
  }
  $scope.changeKichCos = function () {
    $scope.selectedKichCos = $filter('filter')($scope.listSetting.listKichCo, {checked: true});
  }
  $scope.changeMauSacs = function () {
    $scope.selectedMauSacs = $filter('filter')($scope.listSetting.listMauSac, {checked: true});
  }

  $scope.$watchGroup(["selectedDanhMucs","selectedThuongHieus","selectedDayDeos","selectedVatLieus","selectedKichCos","selectedMauSacs","price"], function (newValues, oldValues) {
    if (newValues[0] !== oldValues[0]) {
      $scope.danhMucIds=[];
      $scope.selectedDanhMucs.forEach(element => {
        $scope.danhMucIds.push(element.id)
      });
    }
    if (newValues[1] !== oldValues[1]) {
      $scope.thuongHieuIds=[];
      $scope.selectedThuongHieus.forEach(element => {
        $scope.thuongHieuIds.push(element.idThuongHieu)
      });
    }
    if (newValues[2] !== oldValues[2]) {
      $scope.dayDeoIds=[]
      $scope.selectedDayDeos.forEach(element => {
          $scope.dayDeoIds.push(element.idDayDeo)
      });
    }
    if (newValues[3] !== oldValues[3]) {
      $scope.vatLieuIds=[];
      $scope.selectedVatLieus.forEach(element => {
        $scope.vatLieuIds.push(element.idVatLieu)
      });
      $scope.conditionRequest;
    }
    if (newValues[4] !== oldValues[4]) {
      $scope.kichCoIds=[];
      $scope.selectedKichCos.forEach(element => {
        $scope.kichCoIds.push(element.idKichCo)
      });
    }
    if (newValues[5] !== oldValues[5]) {
      $scope.mauSacIds=[];
      $scope.selectedMauSacs.forEach(element => {
        $scope.mauSacIds.push(element.idMauSac)
      });
    }
    if(newValues[6] !== oldValues[6]){
      console.log(newValues[6]);
    }
  });
  $scope.getSettings = function () {
    $http
      .get(settingAPI)
      .then(function (response) {
        $scope.listSetting = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
  };
  $scope.getSettings();

  if ($scope.searchServiceData == undefined) {
    // $scope.conditionRequest = {
    //   thuongHieuId: [],
    //   danhMucId: [],
    //   sizeId: [],
    //   mauSacId: [],
    //   vatLieuId: [],
    //   dayDeoId: [],
    //   tenSanPham: null
    // };
  }else{
    $scope.danhMucIds = $scope.searchServiceData.danhMucId;
    $scope.thuongHieuIds = $scope.searchServiceData.thuongHieuId;
    $scope.dayDeoIds = $scope.searchServiceData.dayDeoId;
    $scope.vatLieuIds = $scope.searchServiceData.vatLieuId;
    $scope.kichCoIds = $scope.searchServiceData.sizeId;
    $scope.mauSacIds = $scope.searchServiceData.mauSacId;
    $scope.tenSanPham = $scope.searchServiceData.tenSanPham;
  }
  $scope.searchList = function () {
    $scope.conditionRequest = {
      thuongHieuId: $scope.thuongHieuIds,
      danhMucId: $scope.danhMucIds,
      sizeId: $scope.kichCoIds,
      mauSacId: $scope.mauSacIds,
      vatLieuId: $scope.vatLieuIds,
      dayDeoId: $scope.dayDeoIds,
      tenSanPham: $scope.tenSanPham,
    };
    $http
      .post(searchAPI, $scope.conditionRequest)
      .then(function (response) {
        $scope.listSanPham = response.data;
        $scope.totalPages = Math.ceil(
          $scope.listSanPham.length / $scope.pageSize
        );

      })
      .catch(function (error) {
        console.log(error);
      });
  };
  // Kiểm tra dữ liệu khi chuyển trang từ trang chủ
  $scope.searchList();

  $scope.$watchGroup(["listSanPham"], function () {
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
    $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
    $scope.displayedItems.forEach(item=>{
      $scope.getGiaNN(item.sanPhamID)
      $scope.getGiaLN(item.sanPhamID)
    })


  });

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
    $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
    $scope.displayedItems.forEach(item=>{
      $scope.getGiaNN(item.sanPhamID)
      $scope.getGiaLN(item.sanPhamID)
    })
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
      $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
      $scope.displayedItems.forEach(item=>{
        $scope.getGiaNN(item.sanPhamID)
        $scope.getGiaLN(item.sanPhamID)
      })
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
      $scope.displayedItems = $scope.listSanPham.slice(startIndex, endIndex);
      $scope.displayedItems.forEach(item=>{
        $scope.getGiaNN(item.sanPhamID)
        $scope.getGiaLN(item.sanPhamID)
      })
      $scope.checkFirstLastPage();
    }
  };

  $scope.checkFirstLastPage = function (){
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

  $scope.handleSortChange = function() {
    switch ($scope.selectedSortOption) {
      case '1':
        const sortInPrice = [...$scope.listSanPham].sort((a,b)=> {
          console.log(a.sanPhamID);
          console.log($scope.itemWithGiaNN.get(a.sanPhamID));
          console.log(b.sanPhamID);
          console.log($scope.itemWithGiaNN.get(b.sanPhamID));
          return $scope.itemWithGiaNN.get(a.sanPhamID) - $scope.itemWithGiaNN.get(b.sanPhamID);
        });
        $scope.listSanPham = sortInPrice;
        break;
      case '2':
        const sortDePrice = [...$scope.listSanPham].sort((a,b)=> {
          console.log(a.sanPhamID);
          console.log($scope.itemWithGiaNN.get(a.sanPhamID));
          console.log(b.sanPhamID);
          console.log($scope.itemWithGiaNN.get(b.sanPhamID));
          return $scope.itemWithGiaNN.get(b.sanPhamID) - $scope.itemWithGiaNN.get(a.sanPhamID);
        });
        $scope.listSanPham = sortDePrice;
        // $scope.getGiaNN(item.sanPhamID)
        // $scope.getGiaLN(item.sanPhamID)
        break;
      case '3':
        //Sắp xếp tên từ A đến Z
          const sortAZ = [...$scope.listSanPham].sort((a,b)=>a.tenSanPham.localeCompare(b.tenSanPham));
        $scope.listSanPham = sortAZ;
        break;
      case '4':
        // Sắp xếp theo tên sản phẩm từ Z đến A
          const sortZA = [...$scope.listSanPham].sort((a,b)=>b.tenSanPham.localeCompare(a.tenSanPham));
        $scope.listSanPham = sortZA;
        break;
      default:
        // Trường hợp "--" hoặc không xử lý gì cả
    }
  }


  $scope.spkm ;
  $scope.getGiaNN = function (idSanPham){
    $http
        .get(`/api/index/getSPKM?idCTSP=${idSanPham}`)
        .then((resp) => {
          $scope.spkm = resp.data;
          $scope.spkm.sort(function(a, b){return a.giaSanPham - b.giaSanPham});
          $scope.itemWithGiaNN.set(idSanPham,$scope.spkm[0].giaSanPham);
        })
        .catch((e)=>{
          console.log(e);
        });
  }

  $scope.getGiaLN = function (idSanPham){
    $http
        .get(`/api/index/getSPKM?idCTSP=${idSanPham}`)
        .then((resp) => {
          $scope.spkm = resp.data;
          $scope.spkm.sort(function(a, b){return a.giaSanPham - b.giaSanPham});
          $scope.itemWithGiaLN.set(idSanPham,$scope.spkm[$scope.spkm.length-1].giaSanPham);
        })
        .catch((e)=>{
          console.log(e);
        });
  }


  $scope.getGiaNNOld = function (listCT){
    listCT.sort(function(a, b){return a.giaSanPham - b.giaSanPham})
    return listCT[0].giaSanPham;
  }
  $scope.getGiaLNOld = function (listCT){
    listCT.sort(function(a, b){return a.giaSanPham - b.giaSanPham})
    return listCT[listCT.length-1].giaSanPham;
  }
});
