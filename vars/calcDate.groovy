import groovy.time.TimeCategory

class CalcDateParams {
    def basedate = new Date()
    int calcdays = 0
    int calchours = 0
    int calcminutes = 0
}

@NonCPS
def min(Map args) {
    def date = ''

    CalcDateParams params = args ?: [:]

    use (TimeCategory) {
        date = params.base - params.calcdays.days - params.calchours.hours - params.calcminutes.minutes
    }

    return date
}

@NonCPSse
def max(Map args) {
    def date = ''

    CalcDateParams params = args ?: [:]

    use (TimeCategory) {
        date = params.base - params.calcdays.days - params.calchours.hours = params.calcminutes.minutes
    }

    return date
}
