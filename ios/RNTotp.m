
#import "RNTotp.h"
#import "TOTPGenerator.h"
#import "MF_Base32Additions.h"

static NSUInteger defaultDigits = 6;   // 默认位数
static NSTimeInterval defaultPeriod = 60;  // 默认有效时间, 秒

@implementation RNTotp

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(generateOTP:(NSDictionary *)config callback:(RCTResponseSenderBlock)callback) {
    if (!config || config.count == 0 || !config[@"base32String"]) {
        if (callback) callback(@[@""]);
        return;
    }
    
    NSData *secretData =  [NSData dataWithBase32String:config[@"base32String"]];
    TOTPGenerator *generator = [[TOTPGenerator alloc] initWithSecret:secretData
                                                           algorithm:kOTPGeneratorSHA1Algorithm
                                                              digits:[config[@"digits"] integerValue] ? [config[@"digits"] integerValue] : defaultDigits
                                                              period: [config[@"period"] doubleValue] ? [config[@"period"] doubleValue] : defaultPeriod
                                ];
    NSString *str = [generator generateOTP];
    while (str.length < 6) {
        str = [NSString stringWithFormat:@"0%@",str];
    }
    
    if (callback) callback(@[str]);
}

@end
  
