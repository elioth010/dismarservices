'use strict';

describe('Controller Tests', function() {

    describe('OrderRequestRecord Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrderRequestRecord, MockOrderRequest, MockProduct;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrderRequestRecord = jasmine.createSpy('MockOrderRequestRecord');
            MockOrderRequest = jasmine.createSpy('MockOrderRequest');
            MockProduct = jasmine.createSpy('MockProduct');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'OrderRequestRecord': MockOrderRequestRecord,
                'OrderRequest': MockOrderRequest,
                'Product': MockProduct
            };
            createController = function() {
                $injector.get('$controller')("OrderRequestRecordDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'dismarservicesApp:orderRequestRecordUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
