(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('Mahasiswa', Mahasiswa);

    Mahasiswa.$inject = ['$resource'];

    function Mahasiswa ($resource) {
        var resourceUrl =  'api/mahasiswas/:id';

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
            'update': { method:'PUT' },
            'getSeminar': {
                url: 'api/mahasiswas/:id/seminar',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'getKps': {
                url: 'api/mahasiswas/:id/kps',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            }
        });
    }
})();
