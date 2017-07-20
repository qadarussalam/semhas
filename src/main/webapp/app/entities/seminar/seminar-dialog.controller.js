(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('SeminarDialogController', SeminarDialogController);

    SeminarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Seminar', 'Mahasiswa', 'JadwalSeminar', 'Dosen', 'PesertaSeminar'];

    function SeminarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Seminar, Mahasiswa, JadwalSeminar, Dosen, PesertaSeminar) {
        var vm = this;

        vm.seminar = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.mahasiswas = Mahasiswa.query({filter: 'seminar(judul)-is-null'});
        $q.all([vm.seminar.$promise, vm.mahasiswas.$promise]).then(function() {
            if (!vm.seminar.mahasiswaId) {
                return $q.reject();
            }
            return Mahasiswa.get({id : vm.seminar.mahasiswaId}).$promise;
        }).then(function(mahasiswa) {
            vm.mahasiswas.push(mahasiswa);
        });
        vm.jadwalseminars = JadwalSeminar.query({filter: 'seminar(judul)-is-null'});
        $q.all([vm.seminar.$promise, vm.jadwalseminars.$promise]).then(function() {
            if (!vm.seminar.jadwalSeminarId) {
                return $q.reject();
            }
            return JadwalSeminar.get({id : vm.seminar.jadwalSeminarId}).$promise;
        }).then(function(jadwalSeminar) {
            vm.jadwalseminars.push(jadwalSeminar);
        });
        vm.dosens = Dosen.query();
        vm.pesertaseminars = PesertaSeminar.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.seminar.id !== null) {
                Seminar.update(vm.seminar, onSaveSuccess, onSaveError);
            } else {
                Seminar.save(vm.seminar, onSaveSuccess, onSaveError);
            }
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
        vm.datePickerOpenStatus.jamMulai = false;
        vm.datePickerOpenStatus.jamSelesai = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
