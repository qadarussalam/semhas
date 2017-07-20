(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('MahasiswaDialogController', MahasiswaDialogController);

    MahasiswaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Mahasiswa', 'User', 'Jurusan', 'Seminar', 'PesertaSeminar'];

    function MahasiswaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Mahasiswa, User, Jurusan, Seminar, PesertaSeminar) {
        var vm = this;

        vm.mahasiswa = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.jurusans = Jurusan.query();
        vm.seminars = Seminar.query();
        vm.pesertaseminars = PesertaSeminar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mahasiswa.id !== null) {
                Mahasiswa.update(vm.mahasiswa, onSaveSuccess, onSaveError);
            } else {
                Mahasiswa.save(vm.mahasiswa, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:mahasiswaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFoto = function ($file, mahasiswa) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        mahasiswa.foto = base64Data;
                        mahasiswa.fotoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
