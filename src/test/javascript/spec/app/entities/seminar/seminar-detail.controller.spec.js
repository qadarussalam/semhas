'use strict';

describe('Controller Tests', function() {

    describe('Seminar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSeminar, MockMahasiswa, MockJadwalSeminar, MockDosen, MockPesertaSeminar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSeminar = jasmine.createSpy('MockSeminar');
            MockMahasiswa = jasmine.createSpy('MockMahasiswa');
            MockJadwalSeminar = jasmine.createSpy('MockJadwalSeminar');
            MockDosen = jasmine.createSpy('MockDosen');
            MockPesertaSeminar = jasmine.createSpy('MockPesertaSeminar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Seminar': MockSeminar,
                'Mahasiswa': MockMahasiswa,
                'JadwalSeminar': MockJadwalSeminar,
                'Dosen': MockDosen,
                'PesertaSeminar': MockPesertaSeminar
            };
            createController = function() {
                $injector.get('$controller')("SeminarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'semhasApp:seminarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
