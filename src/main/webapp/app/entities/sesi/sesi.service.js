(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('Sesi', Sesi);

    Sesi.$inject = ['$resource', 'DateUtils'];

    function Sesi ($resource, DateUtils) {
        var resourceUrl =  'api/sesis/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.jamMulai = DateUtils.convertDateTimeFromServer(data.jamMulai);
                        data.jamSelesai = DateUtils.convertDateTimeFromServer(data.jamSelesai);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
