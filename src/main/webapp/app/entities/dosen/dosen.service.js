(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('Dosen', Dosen);

    Dosen.$inject = ['$resource'];

    function Dosen ($resource) {
        var resourceUrl =  'api/dosens/:id';

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
