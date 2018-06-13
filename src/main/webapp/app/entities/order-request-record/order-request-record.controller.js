(function() {
    'use strict';

    angular
        .module('dismarservicesApp')
        .controller('OrderRequestRecordController', OrderRequestRecordController);

    OrderRequestRecordController.$inject = ['OrderRequestRecord'];

    function OrderRequestRecordController(OrderRequestRecord) {

        var vm = this;

        vm.orderRequestRecords = [];

        loadAll();

        function loadAll() {
            OrderRequestRecord.query(function(result) {
                vm.orderRequestRecords = result;
                vm.searchQuery = null;
            });
        }
    }
})();
