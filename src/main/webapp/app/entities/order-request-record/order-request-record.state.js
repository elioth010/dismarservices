(function() {
    'use strict';

    angular
        .module('dismarservicesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-request-record', {
            parent: 'entity',
            url: '/order-request-record',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dismarservicesApp.orderRequestRecord.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-request-record/order-request-records.html',
                    controller: 'OrderRequestRecordController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderRequestRecord');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-request-record-detail', {
            parent: 'order-request-record',
            url: '/order-request-record/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dismarservicesApp.orderRequestRecord.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-request-record/order-request-record-detail.html',
                    controller: 'OrderRequestRecordDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderRequestRecord');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderRequestRecord', function($stateParams, OrderRequestRecord) {
                    return OrderRequestRecord.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-request-record',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-request-record-detail.edit', {
            parent: 'order-request-record-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-request-record/order-request-record-dialog.html',
                    controller: 'OrderRequestRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderRequestRecord', function(OrderRequestRecord) {
                            return OrderRequestRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-request-record.new', {
            parent: 'order-request-record',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-request-record/order-request-record-dialog.html',
                    controller: 'OrderRequestRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                subTotal: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-request-record', null, { reload: 'order-request-record' });
                }, function() {
                    $state.go('order-request-record');
                });
            }]
        })
        .state('order-request-record.edit', {
            parent: 'order-request-record',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-request-record/order-request-record-dialog.html',
                    controller: 'OrderRequestRecordDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderRequestRecord', function(OrderRequestRecord) {
                            return OrderRequestRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-request-record', null, { reload: 'order-request-record' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-request-record.delete', {
            parent: 'order-request-record',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-request-record/order-request-record-delete-dialog.html',
                    controller: 'OrderRequestRecordDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderRequestRecord', function(OrderRequestRecord) {
                            return OrderRequestRecord.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-request-record', null, { reload: 'order-request-record' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
