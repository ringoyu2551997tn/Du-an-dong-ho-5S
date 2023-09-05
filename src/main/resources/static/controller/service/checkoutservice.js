myApp.service('checkOutDataService', function() {
    var dataCheckOut;
  
    this.setData = function(value) {
        dataCheckOut = value;
    };
  
    this.getData = function() {
      return dataCheckOut;
    };
  });