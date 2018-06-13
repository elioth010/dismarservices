(function() {
    'use strict';
    angular
        .module('dismarservicesApp')
        .factory('OrderRequestRecord', OrderRequestRecord);

    OrderRequestRecord.$inject = ['$resource'];

    function OrderRequestRecord ($resource) {
        var resourceUrl =  'api/order-request-records/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
