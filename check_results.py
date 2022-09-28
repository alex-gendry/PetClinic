import json
import argparse


class bcolors:
    HEADER = '\033[95m'
    OKBLUE = '\033[94m'
    OKCYAN = '\033[96m'
    OKGREEN = '\033[92m'
    WARNING = '\033[93m'
    FAIL = '\033[91m'
    ENDC = '\033[0m'
    BOLD = '\033[1m'
    UNDERLINE = '\033[4m'


def compare_param_fields(param1, param2):
    if param1["Exclude"] != param2["Exclude"]:
        return False
    if param1["DataTypes"] != param2["DataTypes"]:
        return False
    if param1["NameMatches"] != param2["NameMatches"]:
        return False
    if param1["Findings"][0]["FindingType"] != param2["Findings"][0]["FindingType"]:
        return False

    return True


def check_for_result_in_json_file(path_match, http_method_matches, parameter, json):
    match = False

    for finding in json["Rules"]:
        if http_method_matches != finding["HttpMethodMatches"]:
            continue

        for path_matches in finding["PathMatches"]:
            if isinstance(path_matches, list):
                for this_path_match in path_matches:
                    if this_path_match != path_match:
                        continue
                    if isinstance(finding["Parameters"], list):
                        for this_param in finding["Parameters"]:
                            if compare_param_fields(this_param, parameter):
                                return True
                    else:
                        this_param = finding["Parameters"]
                        if compare_param_fields(this_param, parameter):
                            return True
            else:
                this_path_match = path_matches
                if this_path_match != path_match:
                    continue
                if isinstance(finding["Parameters"], list):
                    for this_param in finding["Parameters"]:
                        if compare_param_fields(this_param, parameter):
                            return True
                else:
                    this_param = finding["Parameters"]
                    if compare_param_fields(this_param, parameter):
                        return True

    return False


def process_args():
    # Setup argument parsing
    parser = argparse.ArgumentParser(
        description="Gather expected and result files")
    parser.add_argument('expected', type=str, help='File with expected results',
                        default='expected.json')
    parser.add_argument('actual', type=str, help='File with actual results',
                        default='requesttypes.json')
    return parser.parse_args()


if __name__ == "__main__":
    args = process_args()

    expected_file = open(args.expected)
    actual_file = open(args.actual)

    expected = json.load(expected_file)
    actual = json.load(actual_file)

    count = 0
    matched = 0
    for finding in expected["Rules"]:
        these_http_method_matches = finding["HttpMethodMatches"]

        for this_path_match in finding["PathMatches"]:
            if isinstance(finding["Parameters"], list):
                for vulnerable_param in finding["Parameters"]:
                    count += 1
                    if check_for_result_in_json_file(this_path_match, these_http_method_matches, vulnerable_param, actual):
                        matched += 1
                        print(
                            f"{bcolors.OKGREEN}Matched{bcolors.ENDC}: Path={this_path_match},  Param={vulnerable_param['NameMatches'][0]}, Type={vulnerable_param['DataTypes'][0]}")
                    else:
                        print(
                            f"{bcolors.FAIL}Didn't Match{bcolors.ENDC}: Path={this_path_match}, Param={vulnerable_param['NameMatches'][0]}, Type={vulnerable_param['DataTypes'][0]}")
            else:
                vulnerable_param = finding["Parameters"]
                count += 1
                if check_for_result_in_json_file(this_path_match, these_http_method_matches, vulnerable_param, actual):
                    matched += 1
                    print(
                        f"{bcolors.OKGREEN}Matched{bcolors.ENDC}: Path={this_path_match},  Param={vulnerable_param['NameMatches'][0]}, Type={vulnerable_param['DataTypes'][0]}")
                else:
                    print(
                        f"{bcolors.FAIL}Didn't Match{bcolors.ENDC}: Path={this_path_match}, Param={vulnerable_param['NameMatches'][0]}, Type={vulnerable_param['DataTypes'][0]}")

    print(
        f"Matched {bcolors.OKCYAN}{matched}{bcolors.ENDC}/{bcolors.OKGREEN}{count}{bcolors.ENDC}")

    if count > matched:
        print(f"Missing {bcolors.FAIL} {count - matched} expected{bcolors.ENDC}")
