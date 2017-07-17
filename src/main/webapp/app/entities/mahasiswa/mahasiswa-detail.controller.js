(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('MahasiswaDetailController', MahasiswaDetailController);

    MahasiswaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Mahasiswa', 'Jurusan', 'Seminar', 'PesertaSeminar'];

    function MahasiswaDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Mahasiswa, Jurusan, Seminar, PesertaSeminar) {
        var vm = this;

        vm.mahasiswa = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('semhasApp:mahasiswaUpdate', function(event, result) {
            vm.mahasiswa = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
