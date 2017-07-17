(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SeminarDetailController', SeminarDetailController);

    SeminarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Seminar', 'Mahasiswa', 'JadwalSeminar', 'Dosen', 'PesertaSeminar'];

    function SeminarDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Seminar, Mahasiswa, JadwalSeminar, Dosen, PesertaSeminar) {
        var vm = this;

        vm.seminar = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('semhasApp:seminarUpdate', function(event, result) {
            vm.seminar = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
