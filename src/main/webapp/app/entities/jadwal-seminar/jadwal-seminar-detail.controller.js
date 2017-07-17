(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('JadwalSeminarDetailController', JadwalSeminarDetailController);

    JadwalSeminarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'JadwalSeminar', 'Sesi', 'Ruang', 'Seminar'];

    function JadwalSeminarDetailController($scope, $rootScope, $stateParams, previousState, entity, JadwalSeminar, Sesi, Ruang, Seminar) {
        var vm = this;

        vm.jadwalSeminar = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('semhasApp:jadwalSeminarUpdate', function(event, result) {
            vm.jadwalSeminar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
