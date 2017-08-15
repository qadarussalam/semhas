(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('MySeminarDialogController', MySeminarDialogController);

    MySeminarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$localStorage', 'Principal', 'DataUtils', 'entity', 'Seminar', 'Mahasiswa', 'Dosen'];

    function MySeminarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $localStorage, Principal, DataUtils, entity, Seminar, Mahasiswa, Dosen) {
        var vm = this;

        vm.seminar = entity;

        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        vm.dosens = Dosen.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            var token = $localStorage.authenticationToken;
            var data = jwt_decode(token);
            
            vm.seminar.mahasiswaId = data['semhas.mhsw'];
            vm.seminar.status = 'MENUNGGU_PERSETUJUAN';

            Seminar.save(vm.seminar, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('semhasApp:seminarUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.setFileAccSeminar = function ($file, seminar) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        seminar.fileAccSeminar = base64Data;
                        seminar.fileAccSeminarContentType = $file.type;
                    });
                });
            }
        };
    }
})();
