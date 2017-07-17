(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('Ruang', Ruang);

    Ruang.$inject = ['$resource'];

    function Ruang ($resource) {
        var resourceUrl =  'api/ruangs/:id';

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
