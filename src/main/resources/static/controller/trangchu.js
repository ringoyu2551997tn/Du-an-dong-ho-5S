myApp.controller("homeCtrl", function ($scope, $http,$window,$rootScope,checkSearchService,checkOutDataService,$location) {
    $scope.listSetting = {};
    $scope.newSanPhams = [];
    $scope.hotSanPhams = [];
    $scope.featureSanPhams = [];
    $scope.itemWithGiaNN = new Map();
    $scope.itemWithGiaLN = new Map();
    $scope.idSanPham ="" ;
    $scope.run =0;
    $scope.init = function () {
        //load product
        $http.get(`/api/index`).then((resp) => {
            $scope.hotSanPhams = resp.data.listSPbanChay;
            // console.log(resp.data.listSPbanChay);
            $scope.newSanPhams = resp.data.listSPmoiNhat;
            $scope.featureSanPhams = resp.data.listSPNoiBat;
            $scope.hotSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
            $scope.newSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
            $scope.featureSanPhams.forEach(item=>{
                $scope.getGiaNN(item.idSanPham)
                $scope.getGiaLN(item.idSanPham)
            })
        });
        $rootScope.currentDate = new Date().toISOString();
    };
    $scope.init();

    //cart
    $scope.cart = [];
    $scope.total = 0;
    $scope.totalSp = 0;
    let currentUser = JSON.parse(localStorage.getItem("currentUser"));
    $rootScope.index = function () {
        if(currentUser != null) {
            $http.get(`/api/giohang/${currentUser.idKhachHang}`).then((resp) => {
                $scope.cart = resp.data;
                // console.log($scope.cart);
            }).catch(error => {
                if(error.status == 403) {
                return null ;
                }
            });
        }else{
            return null ;
        }
    };
    $rootScope.index();
    $scope.setTotals = function (item) {

        if (item) {
            $scope.total += item.giaBan * item.soLuongSanPham;
            $scope.totalSp +=  item.soLuongSanPham;
        }
    };
    $scope.removeSP= function (item) {
        if (item) {
            $scope.delete(item);
            $scope.total -= item.giaBan * item.soLuongSanPham;
            $scope.totalSp -= item.soLuongSanPham;
        }
    };
    //api xóa giohang
    $scope.delete = function (item){
        $http.delete(`/api/giohang/delete/${item.idChiTietGioHang}`)
            .then(resp =>{
                const index = $scope.cart.findIndex(p => p.idChiTietGioHang ==  item.idChiTietGioHang);
                $scope.cart.splice(index,1);
                // alert("xoa thanh cong");
            })
            .catch(error =>{
                alert("Loi roi");
                console.log("eror"+ error);
            })
    }
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
        $location.path("/checkout");
    };
    // kt cart



    $scope.spkm ;
    $scope.getGiaNN = function (idSanPham){
        $http
            .get(`/api/index/getSPKM?idCTSP=${idSanPham}`)
            .then((resp) => {
                $scope.spkm = resp.data;
                $scope.spkm.sort(function(a, b){return a.giaSanPham - b.giaSanPham});
                $scope.itemWithGiaNN.set(idSanPham,$scope.spkm[0].giaSanPham);
                // console.log($scope.itemWithGiaNN.get(idSanPham));
            })
            .catch((e)=>{
                // console.log(e);
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
                // console.log(e);
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

    $scope.searchDanhMuc = function (id){
        checkSearchService.setData(
            {
                thuongHieuId: [],
                danhMucId: [id],
                sizeId: [],
                mauSacId: [],
                vatLieuId: [],
                dayDeoId: [],
                tenSanPham: null
            }
        );
        $location.path("/sanpham");
    }
    $scope.searchThuongHieu = function (id){
        checkSearchService.setData(
            {
                thuongHieuId: [id],
                danhMucId: [],
                sizeId: [],
                mauSacId: [],
                vatLieuId: [],
                dayDeoId: [],
                tenSanPham: null
            }
        );
        $location.path("/sanpham");
    }
    $scope.searchDayDeo = function (id){
        checkSearchService.setData(
            {
                thuongHieuId: [],
                danhMucId: [],
                sizeId: [],
                mauSacId: [],
                vatLieuId: [],
                dayDeoId: [id],
                tenSanPham: null
            }
        );
        $location.path("/sanpham");
    }
    $scope.searchVatLieu = function (id){
        checkSearchService.setData(
            {
                thuongHieuId: [],
                danhMucId: [],
                sizeId: [],
                mauSacId: [],
                vatLieuId: [id],
                dayDeoId: [],
                tenSanPham: null
            }
        );
        $location.path("/sanpham");
    }
})