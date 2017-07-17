(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('JadwalSeminar', JadwalSeminar);

    JadwalSeminar.$inject = ['$resource', 'DateUtils'];

    function JadwalSeminar ($resource, DateUtils) {
        var resourceUrl =  'api/jadwal-seminars/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.tanggal = DateUtils.convertDateTimeFromServer(data.tanggal);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
