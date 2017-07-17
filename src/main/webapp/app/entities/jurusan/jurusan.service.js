(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('Jurusan', Jurusan);

    Jurusan.$inject = ['$resource'];

    function Jurusan ($resource) {
        var resourceUrl =  'api/jurusans/:id';

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
