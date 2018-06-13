(function() {
    'use strict';

    angular
        .module('dismarservicesApp')
        .controller('OrderRequestRecordDetailController', OrderRequestRecordDetailController);

    OrderRequestRecordDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderRequestRecord', 'OrderRequest', 'Product'];

    function OrderRequestRecordDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderRequestRecord, OrderRequest, Product) {
        var vm = this;

        vm.orderRequestRecord = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dismarservicesApp:orderRequestRecordUpdate', function(event, result) {
            vm.orderRequestRecord = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
