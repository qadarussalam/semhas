(function() {
    'use strict';
    angular
        .module('semhasApp')
        .factory('Seminar', Seminar);

    Seminar.$inject = ['$resource', 'DateUtils'];

    function Seminar ($resource, DateUtils) {
        var resourceUrl =  'api/seminars/:id';

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
            'update': { method:'PUT' },
            'getPeserta': {
                url: 'api/seminars/:id/pesertas',
                method: 'GET',
                isArray: true
            }
        });
    }
})();
