'use strict';

describe('Controller Tests', function() {

    describe('JadwalSeminar Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockJadwalSeminar, MockSesi, MockRuang, MockSeminar;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockJadwalSeminar = jasmine.createSpy('MockJadwalSeminar');
            MockSesi = jasmine.createSpy('MockSesi');
            MockRuang = jasmine.createSpy('MockRuang');
            MockSeminar = jasmine.createSpy('MockSeminar');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'JadwalSeminar': MockJadwalSeminar,
                'Sesi': MockSesi,
                'Ruang': MockRuang,
                'Seminar': MockSeminar
            };
            createController = function() {
                $injector.get('$controller')("JadwalSeminarDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'semhasApp:jadwalSeminarUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
