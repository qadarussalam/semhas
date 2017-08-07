(function() {
    'use strict';

    angular
        .module('semhasApp')
        .controller('KpsController', KpsController);

    KpsController.$inject = ['$state', 'PesertaSeminar', 'Seminar', 'Mahasiswa'];

    function KpsController($state, PesertaSeminar, Seminar, Mahasiswa) {
        var vm = this;

        var token = localStorage['jhi-authenticationToken'];
        var data = jwt_decode(token);

        vm.mahasiswaId = data['semhas.mhsw'];

        Mahasiswa.getKps({
            id: vm.mahasiswaId
        }, function(data) {
            vm.seminars = data.seminars;
            
            angular.forEach(data.seminars, function(seminar) {
                seminar.jadwalSeminarTanggal = moment(seminar.jadwalSeminarTanggal).format('DD MMMM YYYY');
                seminar.jamMulai = moment(seminar.jamMulai).format('HH:mm');
                seminar.jamSelesai = moment(seminar.jamSelesai).format('HH:mm');

                Mahasiswa.get({id: seminar.mahasiswaId}, function(mhs) {
                    seminar.mahasiswa = mhs;
                });
            });
        });
    }
})();
