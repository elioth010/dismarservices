(function() {
    'use strict';

    angular
        .module('dismarservicesApp')
        .controller('OrderRequestRecordDialogController', OrderRequestRecordDialogController);

    OrderRequestRecordDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderRequestRecord', 'OrderRequest', 'Product'];

    function OrderRequestRecordDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderRequestRecord, OrderRequest, Product) {
        var vm = this;

        vm.orderRequestRecord = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orderrequests = OrderRequest.query();
        vm.products = Product.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderRequestRecord.id !== null) {
                OrderRequestRecord.update(vm.orderRequestRecord, onSaveSuccess, onSaveError);
            } else {
                OrderRequestRecord.save(vm.orderRequestRecord, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dismarservicesApp:orderRequestRecordUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
