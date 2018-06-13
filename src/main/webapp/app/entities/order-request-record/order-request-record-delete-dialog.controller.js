(function() {
    'use strict';

    angular
        .module('dismarservicesApp')
        .controller('OrderRequestRecordDeleteController',OrderRequestRecordDeleteController);

    OrderRequestRecordDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderRequestRecord'];

    function OrderRequestRecordDeleteController($uibModalInstance, entity, OrderRequestRecord) {
        var vm = this;

        vm.orderRequestRecord = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderRequestRecord.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
