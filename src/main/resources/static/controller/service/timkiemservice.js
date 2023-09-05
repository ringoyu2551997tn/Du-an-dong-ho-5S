myApp.service('checkSearchService', function() {
    var dataSearch;

    this.setData = function(value) {
        dataSearch = value;
    };

    this.getData = function() {
        return dataSearch;
    };
});