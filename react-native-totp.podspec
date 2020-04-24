require 'json'
package_json = JSON.parse(File.read(File.join(__dir__, 'package.json')))

Pod::Spec.new do |s|

  s.name           = package_json["name"]
  s.version        = package_json["version"]
  s.summary        = package_json["description"]
  s.homepage       = package_json["homepage"]
  s.license        = package_json["license"]
  s.author         = { package_json["author"] => package_json["author"] }
  s.platform       = :ios, "9.0"
  s.source         = { :git => "https://github.com/laowenlu/react-native-totp.git" } 
  s.source_files   = 'ios/*.{h,m}'

  s.dependency 'React'

end
